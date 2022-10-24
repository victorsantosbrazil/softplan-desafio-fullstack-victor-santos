import { CreateUserUserCaseRequest } from "./models";

export default interface CreateUserUserCase {
  run(request: CreateUserUserCaseRequest): Promise<void>;
}
