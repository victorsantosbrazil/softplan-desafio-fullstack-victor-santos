import { AxiosError } from "axios";
import { injectable } from "inversify/lib/annotation/injectable";
import { ValidationException } from "../exceptions/ValidationException";
import BFFClientExceptionHandler from "./BFFClientExceptionHandler";

@injectable()
export default class AxiosBFFClientExceptionHandler
  implements BFFClientExceptionHandler
{
  handle(error: Error): void {
    if (error instanceof AxiosError) {
      const responseData = (error.response?.data as any) || {};

      if (responseData.error === "VALIDATION") {
        this._handleValidationErrors(responseData);
      }
    }

    throw error;
  }

  private _handleValidationErrors(responseData: any) {
    const violations = this._getValidationExceptionViolations(responseData);
    throw new ValidationException(responseData.message, violations);
  }

  private _getValidationExceptionViolations(responseData: any) {
    const violations = new Map<string, string[]>();

    for (const violation of responseData.violations) {
      const propertyPath = violation.propertyPath;
      const propertyViolation = violation.errorMessage;

      let propertyViolations = violations.get(propertyPath);

      if (!propertyViolations) {
        propertyViolations = [];
      }

      propertyViolations.push(propertyViolation);

      violations.set(propertyPath, propertyViolations);
    }
    return violations;
  }
}
