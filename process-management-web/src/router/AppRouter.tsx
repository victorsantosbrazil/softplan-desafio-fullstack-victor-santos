import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoggedArea from "../templates/LoggedArea";
import UsersForm from "../users/admin/views/UsersForm";
import LoggedAreaRouter from "./LoggedAreaRouter";

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="*"
          element={<LoggedArea children={<LoggedAreaRouter />} />}
        ></Route>
        <Route path="/login" element={<UsersForm />}></Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
