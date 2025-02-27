import { Page, PageParams } from "../../../../common/utils/pagination";
import { GetUsersUserCaseResponse } from "./GetUsersUserCaseResponse";

export default interface GetUsersUserCase {
  run(pageParams?: PageParams): Promise<Page<GetUsersUserCaseResponse>>;
}
