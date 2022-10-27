export default class LoginUserCaseRequest {
  email: string;
  password: string;

  constructor(attrs: { email: string; password: string }) {
    this.email = attrs.email;
    this.password = attrs.password;
  }
}
