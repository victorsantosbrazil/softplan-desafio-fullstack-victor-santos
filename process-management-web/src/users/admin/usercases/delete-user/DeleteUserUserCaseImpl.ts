import { inject, injectable } from "inversify";
import BFFClient from "../../../../clients/BFFClient";
import { clients } from "../../../../diSymbols";
import GetUsersUserCase from "./DeleteUserUserCase";

@injectable()
export default class DeleteUserUserCaseImpl implements GetUsersUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(id: string): Promise<void> {
    await this._bffClient.delete("/users/" + id);
  }
}
