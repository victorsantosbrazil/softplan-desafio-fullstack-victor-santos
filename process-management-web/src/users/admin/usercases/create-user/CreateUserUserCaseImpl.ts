import { inject, injectable } from "inversify";
import BFFClient from "../../../../clients/BFFClient";
import { clients } from "../../../../diSymbols";
import CreateUserUserCase from "./CreateUserUserCase";
import { CreateUserUserCaseRequest } from "./models";

@injectable()
export default class CreateUserUserCaseImpl implements CreateUserUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(request: CreateUserUserCaseRequest): Promise<void> {
    await this._bffClient.post("/users", request);
  }
}
