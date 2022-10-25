import { AxiosError, AxiosResponse } from "axios";
import { ValidationException } from "../../exceptions/ValidationException";
import AxiosBFFClientExceptionHandler from "../AxiosBFFClientExceptionHandler";

describe("AxiosBFFClientExceptionHandler tests", () => {
  const exceptionHandler = new AxiosBFFClientExceptionHandler();

  it("should throw validation exception when validation error", () => {
    const axiosResponseDataViolations = [
      { propertyPath: "name", errorMessage: "name is required" },
      {
        propertyPath: "password",
        errorMessage: "password should have at least 1 number",
      },
      {
        propertyPath: "password",
        errorMessage: "password should have at least 1 special character",
      },
    ];

    const axiosResponseData = {
      error: "VALIDATION",
      message: "Validation error",
      violations: axiosResponseDataViolations,
    };

    const axiosResponse: Partial<AxiosResponse> = {
      data: axiosResponseData,
    };

    const axiosError = new AxiosError(
      "Request failed with status code 400",
      "400",
      {},
      {},
      axiosResponse as any
    );

    const expectedViolations = new Map<string, string[]>();

    expectedViolations.set("name", [
      axiosResponseDataViolations[0].errorMessage,
    ]);

    expectedViolations.set("password", [
      axiosResponseDataViolations[1].errorMessage,
      axiosResponseDataViolations[2].errorMessage,
    ]);

    const expectedException = new ValidationException(
      axiosResponse.data.message,
      expectedViolations
    );

    try {
      exceptionHandler.handle(axiosError);
    } catch (error) {
      const responseError = error as ValidationException;
      expect(responseError).toEqual(expectedException);
      expect(responseError.violations).toEqual(expectedViolations);
    }
  });

  it("when throw not handled axios error then re-throw it", () => {
    const axiosResponse: Partial<AxiosResponse> = {
      data: {
        error: "UNKNOWN_ERROR",
        message: "Unknown error",
      },
    };

    const axiosError = new AxiosError(
      "Request failed with status code 500",
      "500",
      {},
      {},
      axiosResponse as any
    );

    const t = () => exceptionHandler.handle(axiosError);

    expect(t).toThrow(axiosError);
  });

  it("when unexpected error then re-throw", () => {
    const error = new Error();

    const t = () => exceptionHandler.handle(error);

    expect(t).toThrow(Error);
  });
});
