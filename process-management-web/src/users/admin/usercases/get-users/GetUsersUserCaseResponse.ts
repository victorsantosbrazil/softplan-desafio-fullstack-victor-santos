export class GetUsersUserCaseResponse {
  id: string;
  name: string;
  role: string;

  constructor(attrs: { id: string; name: string; role: string }) {
    this.id = attrs.id;
    this.name = attrs.name;
    this.role = attrs.role;
  }
}
