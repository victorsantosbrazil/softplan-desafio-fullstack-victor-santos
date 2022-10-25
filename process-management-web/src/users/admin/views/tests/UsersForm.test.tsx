import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { UserRole } from "../../../../security/UserRole";
import { toast } from "react-toastify";
import UsersForm from "../UsersForm";
import { ValidationException } from "../../../../exceptions/ValidationException";
import { usercases } from "../../../../diSymbols";
import { waitComponentBeStable } from "../../../../testUtils";
import { useParams } from "react-router-dom";

const mockNavigate = jest.fn();

const mockCreateUserCase = {
  run: jest.fn(),
};

const mockGetUserUserCase = {
  run: jest.fn(),
};

const mockUpdateUserUserCase = {
  run: jest.fn(),
};

jest.mock("react-router-dom", () => {
  return {
    useNavigate: () => mockNavigate,
    useParams: jest.fn(() => {
      return {};
    }),
  };
});

jest.mock("react-toastify");

jest.mock("inversify-react", () => {
  const useInjection = (identifier: Symbol) => {
    switch (identifier) {
      case usercases.CREATE_USER:
        return mockCreateUserCase;
      case usercases.GET_USER:
        return mockGetUserUserCase;
      case usercases.UPDATE_USER:
        return mockUpdateUserUserCase;
    }
    throw Error("Dependency not found");
  };

  return {
    __esModule: true,
    useInjection,
  };
});

describe("UsersForm tests", () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it("should renders correctly", () => {
    render(<UsersForm />);

    const form = screen.getByTestId("form");

    const nameFormGroup = screen.getByTestId("form-group-name");
    const nameFormLabel = screen.getByTestId("form-label-name");
    const nameFormControl = screen.getByTestId("form-control-name");

    expect(form).toContainElement(nameFormGroup);
    expect(nameFormGroup).toContainElement(nameFormLabel);
    expect(nameFormLabel).toHaveTextContent("Name");
    expect(nameFormLabel).toHaveAttribute("for", "name-input");
    expect(nameFormGroup).toContainElement(nameFormControl);
    expect(nameFormControl).toHaveAttribute("id", "name-input");
    expect(nameFormControl).toHaveAttribute("aria-required", "true");

    const emailFormGroup = screen.getByTestId("form-group-email");
    const emailFormLabel = screen.getByTestId("form-label-email");
    const emailFormControl = screen.getByTestId("form-control-email");

    expect(form).toContainElement(emailFormGroup);
    expect(emailFormGroup).toContainElement(emailFormLabel);
    expect(emailFormLabel).toHaveTextContent("Email");
    expect(emailFormLabel).toHaveAttribute("for", "email-input");
    expect(emailFormControl).toHaveAttribute("type", "email");
    expect(emailFormGroup).toContainElement(emailFormControl);
    expect(emailFormControl).toHaveAttribute("id", "email-input");
    expect(emailFormControl).toHaveAttribute("aria-required", "true");

    const passwordFormGroup = screen.getByTestId("form-group-password");
    const passwordFormLabel = screen.getByTestId("form-label-password");
    const passwordFormControl = screen.getByTestId("form-control-password");

    expect(form).toContainElement(passwordFormGroup);
    expect(passwordFormGroup).toContainElement(passwordFormLabel);
    expect(passwordFormLabel).toHaveTextContent("Password");
    expect(passwordFormLabel).toHaveAttribute("for", "password-input");
    expect(passwordFormControl).toHaveAttribute("type", "password");
    expect(passwordFormGroup).toContainElement(passwordFormControl);
    expect(passwordFormControl).toHaveAttribute("id", "password-input");
    expect(passwordFormControl).toHaveAttribute("aria-required", "true");

    const roleFormGroup = screen.getByTestId("form-group-role");
    const roleFormLabel = screen.getByTestId("form-label-role");
    const roleFormControl = screen.getByTestId("form-control-role");
    const roleFormControlOptions = screen.getAllByTestId(
      "form-control-role-options"
    );

    expect(roleFormGroup).toContainElement(roleFormLabel);
    expect(roleFormLabel).toHaveTextContent("Role");
    expect(roleFormGroup).toContainElement(roleFormControl);
    expect(roleFormControl).toHaveAttribute("id", "role-input");
    expect(passwordFormControl).toHaveAttribute("aria-required", "true");
    expect(roleFormControl).toContainElement(roleFormControlOptions[0]);
    expect(roleFormControl).toContainElement(roleFormControlOptions[1]);
    expect(roleFormControl).toContainElement(roleFormControlOptions[2]);

    expect(roleFormControlOptions[0]).toHaveTextContent("Admin");
    expect(roleFormControlOptions[1]).toHaveTextContent("Process Screener");
    expect(roleFormControlOptions[2]).toHaveTextContent("Process Finisher");

    const saveButton = screen.getByTestId("save-button");
    expect(saveButton).toHaveTextContent("Save");

    const cancelButton = screen.getByTestId("cancel-button");
    expect(cancelButton).toHaveTextContent("Cancel");
  });

  it("should return to users route when click on cancel button", () => {
    render(<UsersForm />);

    const cancelButton = screen.getByTestId("cancel-button");
    fireEvent.click(cancelButton);
    expect(mockNavigate).toBeCalledWith("/users");
  });

  it("should create user", async () => {
    render(<UsersForm />);

    const submittedData = {
      name: "Joao Batista",
      email: "joao@test.com",
      password: "1234",
      role: UserRole.PROCESS_FINISHER,
    };

    const nameFormControl = screen.getByTestId("form-control-name");

    const emailFormControl = screen.getByTestId("form-control-email");

    const passwordFormControl = screen.getByTestId("form-control-password");

    const roleFormControl = screen.getByTestId("form-control-role");

    fireEvent.change(nameFormControl, {
      target: { value: submittedData.name },
    });

    fireEvent.change(emailFormControl, {
      target: { value: submittedData.email },
    });

    fireEvent.change(passwordFormControl, {
      target: { value: submittedData.password },
    });

    fireEvent.change(roleFormControl, {
      target: { value: submittedData.role },
    });

    const saveButton = screen.getByTestId("save-button");

    fireEvent.click(saveButton);

    await waitFor(() => expect(mockCreateUserCase.run).resolves);

    expect(mockCreateUserCase.run).toBeCalledWith(submittedData);
    expect(mockUpdateUserUserCase.run).not.toBeCalled();

    const expectedMsg = "User saved successfully";

    expect(toast).toBeCalledWith(expectedMsg, {
      style: { background: "#07bc0c", color: "white" },
      theme: "colored",
    });

    expect(mockNavigate).toBeCalledWith("/users");
  });

  it("should show validation errors", async () => {
    render(<UsersForm />);

    const nameErrors = ["name required"];
    const emailErrors = ["email required"];
    const passwordErrors = [
      "password should have at least 1 number",
      "password should have at least 1 special character",
    ];

    const violations = new Map<string, string[]>();
    violations.set("name", nameErrors);
    violations.set("email", emailErrors);
    violations.set("password", passwordErrors);

    const validationException = new ValidationException(
      "Validation Error",
      violations
    );
    mockCreateUserCase.run.mockRejectedValue(validationException);

    const saveButton = screen.getByTestId("save-button");

    fireEvent.click(saveButton);

    await waitFor(() => expect(mockCreateUserCase.run).resolves);

    const nameControlError = await screen.findByTestId(
      "form-control-error-name-0"
    );

    const emailControlError = await screen.findByTestId(
      "form-control-error-email-0"
    );

    const passwordControlError1 = await screen.findByTestId(
      "form-control-error-password-0"
    );

    const passwordControlError2 = await screen.findByTestId(
      "form-control-error-password-1"
    );

    expect(nameControlError).toHaveTextContent(nameErrors[0]);
    expect(emailControlError).toHaveTextContent(emailErrors[0]);
    expect(passwordControlError1).toHaveTextContent(passwordErrors[0]);
    expect(passwordControlError2).toHaveTextContent(passwordErrors[1]);

    const nameFormControl = await screen.findByTestId("form-control-name");
    const emailFormControl = await screen.findByTestId("form-control-email");
    const passwordFormControl = await screen.findByTestId(
      "form-control-password"
    );

    expect(nameFormControl).toHaveAttribute("aria-invalid", "true");
    expect(nameFormControl).toHaveClass("is-invalid");
    expect(emailFormControl).toHaveAttribute("aria-invalid", "true");
    expect(emailFormControl).toHaveClass("is-invalid");
    expect(passwordFormControl).toHaveAttribute("aria-invalid", "true");
    expect(passwordFormControl).toHaveClass("is-invalid");
  });

  it("when editing should fetch user data and update fields", async () => {
    const userId = "fadfdafd";

    const mockUser = {
      id: userId,
      name: "Jefter Oliveira",
      email: "jefter@test.com",
      role: UserRole.PROCESS_FINISHER,
    };

    const mockUseParams = jest.mocked(useParams);
    mockUseParams.mockReturnValue({ id: userId });

    mockGetUserUserCase.run.mockResolvedValue(mockUser);

    render(<UsersForm />);

    expect(mockGetUserUserCase.run).toBeCalledWith(userId);

    await waitFor(() => expect(mockGetUserUserCase.run).resolves);

    await waitComponentBeStable();

    const nameFormControl = await screen.findByTestId("form-control-name");
    const emailFormControl = await screen.findByTestId("form-control-email");
    const roleFormControlOptions: any = await screen.findAllByTestId(
      "form-control-role-options"
    );

    expect(nameFormControl).toHaveAttribute("value", mockUser.name);
    expect(emailFormControl).toHaveAttribute("value", mockUser.email);
    expect(roleFormControlOptions[0].selected).toBeFalsy();
    expect(roleFormControlOptions[1].selected).toBeFalsy();
    expect(roleFormControlOptions[2].selected).toBeTruthy();
  });

  it("when editing should hide password input", async () => {
    const userId = "fadfdafd";

    const mockUser = {
      id: userId,
      name: "Jefter Oliveira",
      email: "jefter@test.com",
      role: "PROCESS_SCREENER",
    };

    const mockUseParams = jest.mocked(useParams);
    mockUseParams.mockReturnValue({ id: userId });

    mockGetUserUserCase.run.mockResolvedValue(mockUser);

    render(<UsersForm />);

    await waitComponentBeStable();

    const passwordGroup = screen.queryByTestId("form-group-password");
    expect(passwordGroup).not.toBeInTheDocument();
  });

  it("should update user", async () => {
    const userId = "lfkdadfdadfda";

    const mockUser = {
      id: userId,
      name: "Jefter Oliveira",
      email: "jefter@test.com",
      role: UserRole.PROCESS_FINISHER,
    };

    const mockUseParams = jest.mocked(useParams);
    mockUseParams.mockReturnValue({ id: userId });

    mockGetUserUserCase.run.mockResolvedValueOnce(mockUser);

    render(<UsersForm />);

    const submittedData = {
      id: userId,
      name: "Jefter Silva",
      email: "jefter.silva@test.com",
      role: UserRole.ADMIN,
    };

    const nameFormControl = screen.getByTestId("form-control-name");

    const emailFormControl = screen.getByTestId("form-control-email");

    const roleFormControl = screen.getByTestId("form-control-role");

    fireEvent.change(nameFormControl, {
      target: { value: submittedData.name },
    });

    fireEvent.change(emailFormControl, {
      target: { value: submittedData.email },
    });

    fireEvent.change(roleFormControl, {
      target: { value: submittedData.role },
    });

    const saveButton = screen.getByTestId("save-button");

    fireEvent.click(saveButton);

    await waitFor(() => expect(mockUpdateUserUserCase.run).resolves);

    expect(mockUpdateUserUserCase.run).toBeCalledWith(submittedData);
    expect(mockCreateUserCase.run).not.toBeCalled();

    const expectedMsg = "User saved successfully";

    expect(toast).toBeCalledWith(expectedMsg, {
      style: { background: "#07bc0c", color: "white" },
      theme: "colored",
    });

    expect(mockNavigate).toBeCalledWith("/users");
  });
});
