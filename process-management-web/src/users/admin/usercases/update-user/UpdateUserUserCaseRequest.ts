import { UserRole } from "../../../../security/UserRole";

export default class UpdateUserUserCaseRequest {
  id: string;
  name: string;
  email: string;
  role: UserRole;

  constructor(attrs: {
    id: string;
    name: string;
    email: string;
    role: UserRole;
  }) {
    this.id = attrs.id;
    this.name = attrs.name;
    this.email = attrs.email;
    this.role = attrs.role;
  }
}
