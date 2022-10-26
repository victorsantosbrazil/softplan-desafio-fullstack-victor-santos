import { Routes, Route, Navigate } from "react-router-dom";
import UsersForm from "../users/admin/views/UsersForm";
import UsersList from "../users/admin/views/UsersList";

const LoggedAreaRouter = () => {
  const defaultRoute = "/users";

  console.log("oi");
  return (
    <Routes location="">
      <Route path="/" element={<Navigate to={defaultRoute} replace />} />
      <Route element={<UsersList />} path="/users"></Route>
      <Route element={<UsersForm />} path="/users/new"></Route>
      <Route element={<UsersForm />} path="/users/:id"></Route>
    </Routes>
  );
};

export default LoggedAreaRouter;
