export class UnauthorizedException extends Error {
  constructor(msg: string) {
    super(msg);

    Object.setPrototypeOf(this, UnauthorizedException.prototype);
  }
}
