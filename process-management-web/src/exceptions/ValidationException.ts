export class ValidationException extends Error {
  violations: Map<string, string[]>;

  constructor(msg: string, violations: Map<string, string[]>) {
    super(msg);
    this.violations = violations;

    Object.setPrototypeOf(this, ValidationException.prototype);
  }
}
