import GetUserUserCaseResponse from "./GetUserUserCaseResponse";

export default interface GetUserUserCase {
  run(id: string): Promise<GetUserUserCaseResponse>;
}
