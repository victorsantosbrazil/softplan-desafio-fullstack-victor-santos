import UpdateUserUserCaseRequest from "./UpdateUserUserCaseRequest";

export default interface UpdateUserUserCase {
  run(request: UpdateUserUserCaseRequest): Promise<void>;
}
