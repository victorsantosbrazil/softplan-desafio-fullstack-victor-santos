import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import UsersForm from "../users/admin/views/UsersForm";
import UsersList from "../users/admin/views/UsersList";

const AppRouter = () => {
  const defaultRoute = "/users";

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to={defaultRoute} replace />} />
        <Route element={<UsersList />} path="/users"></Route>
        <Route element={<UsersForm />} path="/users/new"></Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
