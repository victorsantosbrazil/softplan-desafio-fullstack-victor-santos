import "reflect-metadata";
import { Provider } from "inversify-react";
import "./App.css";
import container from "./diContainer";
import AppRouter from "./router/AppRouter";

function App() {
  return (
    <div className="App">
      <Provider container={container}>
        <AppRouter />
      </Provider>
    </div>
  );
}

export default App;
