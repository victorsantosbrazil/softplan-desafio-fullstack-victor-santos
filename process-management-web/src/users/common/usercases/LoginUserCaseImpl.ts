import { inject, injectable } from "inversify";
import BFFClient from "../../../clients/BFFClient";
import { clients } from "../../../diSymbols";
import LoginUserCase from "./LoginUserCase";
import LoginUserCaseRequest from "./LoginUserCaseRequest";

@injectable()
export default class LoginUserCaseImpl implements LoginUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(request: LoginUserCaseRequest): Promise<string> {
    const { data } = await this._bffClient.post("/auth/basic", request);
    return data.apiToken;
  }
}
