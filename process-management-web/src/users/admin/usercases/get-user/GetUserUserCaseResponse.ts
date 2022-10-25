export default class GetUserUserCaseResponse {
  id: string;
  name: string;
  role: string;
  email: string;

  constructor(attrs: {
    id: string;
    name: string;
    role: string;
    email: string;
  }) {
    this.id = attrs.id;
    this.name = attrs.name;
    this.role = attrs.role;
    this.email = attrs.email;
  }
}
