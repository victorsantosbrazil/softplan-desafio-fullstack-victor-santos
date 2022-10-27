import LoginUserCaseRequest from "./LoginUserCaseRequest";

export default interface LoginUserCase {
  run(request: LoginUserCaseRequest): Promise<string>;
}
