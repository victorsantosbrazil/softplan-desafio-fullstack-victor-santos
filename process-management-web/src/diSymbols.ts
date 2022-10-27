export const clients = {
  BFF_CLIENT: Symbol.for("clients.bff"),
  BFF_CLIENT_EXCEPTION_HANDLER: Symbol.for("clients.bffexceptionhandler"),
};

export const usercases = {
  CREATE_USER: Symbol.for("users.admin.usercases.createusers"),
  GET_USER: Symbol.for("users.admin.usercases.getuser"),
  GET_USERS: Symbol.for("users.admin.usercases.getusers"),
  UPDATE_USER: Symbol.for("users.admin.usercases.updateuser"),
  DELETE_USER: Symbol.for("users.admin.usercases.deleteuser"),
  LOGIN: Symbol.for("users.common.usercases.login"),
};
