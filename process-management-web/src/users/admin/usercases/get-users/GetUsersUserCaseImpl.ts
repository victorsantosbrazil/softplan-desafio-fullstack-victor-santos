import "reflect-metadata";
import { inject, injectable } from "inversify";
import BFFClient from "../../../../clients/BFFClient";
import { PageParams, Page } from "../../../../common/pagination";
import { clients } from "../../../../diSymbols";
import GetUsersUserCase from "./GetUsersUserCase";
import { GetUsersUserCaseResponse } from "./GetUsersUserCaseResponse";
import Pageable from "../../../../common/pagination/Pageable";

@injectable()
export default class GetUsersUserCaseImpl implements GetUsersUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(params?: PageParams): Promise<Page<GetUsersUserCaseResponse>> {
    const { data } = await this._bffClient.get("/users", {
      params: params,
    });

    const content = data.content.map((userData: any) => {
      return new GetUsersUserCaseResponse(userData);
    });

    const pageable = new Pageable({
      pageNumber: data.pageable.pageNumber,
      isFirst: data.first,
      isLast: data.last,
    });

    return new Page(content, pageable);
  }
}
