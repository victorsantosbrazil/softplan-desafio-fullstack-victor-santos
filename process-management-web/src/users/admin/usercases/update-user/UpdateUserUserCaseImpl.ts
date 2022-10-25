import { inject, injectable } from "inversify";
import BFFClient from "../../../../clients/BFFClient";
import { clients } from "../../../../diSymbols";
import UpdateUserUserCase from "./UpdateUserUserCase";
import UpdateUserUserCaseRequest from "./UpdateUserUserCaseRequest";

@injectable()
export default class UpdateUserUserCaseImpl implements UpdateUserUserCase {
  constructor(@inject(clients.BFF_CLIENT) private _bffClient: BFFClient) {}

  async run(request: UpdateUserUserCaseRequest): Promise<void> {
    const id = request.id;
    await this._bffClient.patch(`/users/${id}`, request);
  }
}
