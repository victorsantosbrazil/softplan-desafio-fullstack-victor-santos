import "reflect-metadata";
import { Provider } from "inversify-react";
import "./App.css";
import container from "./diContainer";
import AppRouter from "./router/AppRouter";
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <div className="App">
      <Provider container={container}>
        <ToastContainer />
        <AppRouter />
      </Provider>
    </div>
  );
}

export default App;
