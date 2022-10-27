import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoggedArea from "../templates/LoggedArea";
import LoginForm from "../users/common/views/LoginForm";
import LoggedAreaRouter from "./LoggedAreaRouter";

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="*"
          element={<LoggedArea children={<LoggedAreaRouter />} />}
        ></Route>
        <Route path="/login" element={<LoginForm />}></Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
