import { UserRole } from "../../../../security/UserRole";

export class CreateUserUserCaseRequest {
  name: string;
  email: string;
  password: string;
  role: UserRole;

  constructor(attrs: {
    name: string;
    email: string;
    password: string;
    role: UserRole;
  }) {
    this.name = attrs.name;
    this.email = attrs.email;
    this.password = attrs.password;
    this.role = attrs.role;
  }
}
