import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { toast } from "react-toastify";
import { usercases } from "../../../../diSymbols";
import { UnauthorizedException } from "../../../../exceptions/UnauthorizedException";
import { waitComponentBeStable } from "../../../../testUtils";
import LoginForm from "../LoginForm";

const mockNavigate = jest.fn();

jest.mock("react-router-dom", () => {
  return {
    useNavigate: () => mockNavigate,
  };
});

jest.mock("react-toastify");

const mockLoginUserCase = {
  run: jest.fn(),
};

jest.mock("inversify-react", () => {
  const useInjection = (identifier: Symbol) => {
    switch (identifier) {
      case usercases.LOGIN:
        return mockLoginUserCase;
    }
    throw Error("Dependency not found");
  };

  return {
    __esModule: true,
    useInjection,
  };
});

describe("LoginForm tests", () => {
  it("should renders correctly", () => {
    render(<LoginForm />);

    const form = screen.getByTestId("form");
    const emailFormGroup = screen.getByTestId("email-form-group");
    const emailFormLabel = screen.getByTestId("email-form-label");
    const emailFormControl = screen.getByTestId("email-form-control");
    const passwordFormGroup = screen.getByTestId("password-form-group");
    const passwordFormLabel = screen.getByTestId("password-form-label");
    const passwordFormControl = screen.getByTestId("password-form-control");
    const submitButton = screen.getByTestId("submit-button");

    expect(form).toContainElement(emailFormGroup);
    expect(form).toContainElement(passwordFormGroup);
    expect(emailFormGroup).toContainElement(emailFormLabel);
    expect(emailFormLabel).toHaveTextContent("Email");
    expect(emailFormGroup).toContainElement(emailFormControl);
    expect(emailFormLabel).toHaveAttribute("for", "email-form-control");
    expect(emailFormControl).toHaveAttribute("id", "email-form-control");
    expect(emailFormControl).toHaveAttribute("aria-required", "true");
    expect(passwordFormGroup).toContainElement(passwordFormLabel);
    expect(passwordFormLabel).toHaveTextContent("Password");
    expect(passwordFormGroup).toContainElement(passwordFormControl);
    expect(passwordFormLabel).toHaveAttribute("for", "password-form-control");
    expect(passwordFormControl).toHaveAttribute("id", "password-form-control");
    expect(passwordFormControl).toHaveAttribute("aria-required", "true");
    expect(submitButton).toHaveTextContent("Log in");
  });

  it("should login", async () => {
    render(<LoginForm />);

    const loginFormData = {
      email: "test@test.com",
      password: "1234",
    };

    const emailFormControl = screen.getByTestId("email-form-control");
    const passwordFormControl = screen.getByTestId("password-form-control");

    fireEvent.change(emailFormControl, {
      target: { value: loginFormData.email },
    });

    fireEvent.change(passwordFormControl, {
      target: { value: loginFormData.password },
    });

    waitComponentBeStable();

    const submitButton = screen.getByTestId("submit-button");
    fireEvent.click(submitButton);

    expect(mockLoginUserCase.run).toBeCalledWith({
      email: loginFormData.email,
      password: loginFormData.password,
    });

    await waitFor(() => expect(mockLoginUserCase.run).resolves);
    expect(mockNavigate).toBeCalledWith("/");
  });

  it("should fail to login then show error message", async () => {
    render(<LoginForm />);

    const errorMessage = "Unauthorized";
    mockLoginUserCase.run.mockRejectedValue(
      new UnauthorizedException(errorMessage)
    );

    const submitButton = await screen.findByTestId("submit-button");
    fireEvent.click(submitButton);

    await waitFor(() => expect(mockLoginUserCase.run).rejects);

    expect(toast).toBeCalledWith(errorMessage, {
      className: "bg-danger text-white",
      theme: "colored",
    });
  });
});
