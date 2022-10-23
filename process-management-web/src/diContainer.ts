import { Container } from "inversify";
import * as injectionSymbols from "./diSymbols";

import AxiosBFFClient from "./clients/AxiosBFFClient";
import { GetUsersUserCaseImpl } from "./users/admin/usercases";
import BFFClient from "./clients/BFFClient";

const container = new Container();
container
  .bind<BFFClient>(injectionSymbols.clients.BFF_CLIENT)
  .toDynamicValue(() => new AxiosBFFClient());

container.bind(injectionSymbols.usercases.GET_USERS).to(GetUsersUserCaseImpl);

export default container;
