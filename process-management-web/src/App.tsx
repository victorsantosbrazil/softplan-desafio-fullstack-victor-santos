import "reflect-metadata";
import { Provider } from "inversify-react";
import "./App.css";
import UsersList from "./users/admin/views/UsersList";
import container from "./diContainer";

function App() {
  return (
    <div className="App">
      <Provider container={container}>
        <UsersList />
      </Provider>
    </div>
  );
}

export default App;
