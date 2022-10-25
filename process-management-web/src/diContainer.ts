import { Container } from "inversify";
import * as injectionSymbols from "./diSymbols";

import AxiosBFFClient from "./clients/AxiosBFFClient";
import {
  CreateUserUserCaseImpl,
  GetUsersUserCaseImpl,
} from "./users/admin/usercases";
import BFFClient from "./clients/BFFClient";
import AxiosBFFClientExceptionHandler from "./clients/AxiosBFFClientExceptionHandler";
import BFFClientExceptionHandler from "./clients/BFFClientExceptionHandler";

const container = new Container();

container
  .bind<BFFClientExceptionHandler>(
    injectionSymbols.clients.BFF_CLIENT_EXCEPTION_HANDLER
  )
  .to(AxiosBFFClientExceptionHandler);

container
  .bind<BFFClient>(injectionSymbols.clients.BFF_CLIENT)
  .to(AxiosBFFClient);

container.bind(injectionSymbols.usercases.GET_USERS).to(GetUsersUserCaseImpl);

container
  .bind(injectionSymbols.usercases.CREATE_USERS)
  .to(CreateUserUserCaseImpl);

export default container;
