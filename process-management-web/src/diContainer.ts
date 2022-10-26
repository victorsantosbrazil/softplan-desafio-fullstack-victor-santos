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
import GetUserUserCaseImpl from "./users/admin/usercases/get-user/GetUserUserCaseImpl";
import UpdateUserUserCaseImpl from "./users/admin/usercases/update-user/UpdateUserUserCaseImpl";
import DeleteUserUserCaseImpl from "./users/admin/usercases/delete-user/DeleteUserUserCaseImpl";

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
container.bind(injectionSymbols.usercases.GET_USER).to(GetUserUserCaseImpl);

container
  .bind(injectionSymbols.usercases.CREATE_USER)
  .to(CreateUserUserCaseImpl);

container
  .bind(injectionSymbols.usercases.UPDATE_USER)
  .to(UpdateUserUserCaseImpl);

container
  .bind(injectionSymbols.usercases.DELETE_USER)
  .to(DeleteUserUserCaseImpl);

export default container;
