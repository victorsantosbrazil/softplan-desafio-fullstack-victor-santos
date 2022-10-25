export const clients = {
  BFF_CLIENT: Symbol.for("clients.bff"),
  BFF_CLIENT_EXCEPTION_HANDLER: Symbol.for("clients.bffexceptionhandler"),
};

export const usercases = {
  GET_USERS: Symbol.for("users.admin.usercases.getusers"),
  CREATE_USERS: Symbol.for("users.admin.usercases.createusers"),
};
