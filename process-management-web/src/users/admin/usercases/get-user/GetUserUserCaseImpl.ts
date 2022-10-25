import { inject, injectable } from "inversify";
import BFFClient from "../../../../clients/BFFClient";
import { clients } from "../../../../diSymbols";
import GetUsersUserCase from "./GetUserUserCase";
import GetUserUserCaseResponse from "./GetUserUserCaseResponse";

@injectable()
export default class GetUserUserCaseImpl implements GetUsersUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(id: string): Promise<GetUserUserCaseResponse> {
    const { data } = await this._bffClient.get("/users/" + id);
    return new GetUserUserCaseResponse({ ...data });
  }
}
