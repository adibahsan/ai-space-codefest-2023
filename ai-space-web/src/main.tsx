import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import 'bootstrap/dist/css/bootstrap.css';
import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import ChatView from "./view/ChatView.tsx";
import TrackerView from "./view/TrackerView.tsx";
import Main from "./view/Main.tsx";
import {Provider} from "react-redux";
import store from "./reducer/store.ts";
import LoginPage from "./view/Login/LoginPage.tsx";
import SuccessPage from "./view/SuccessPage/SuccessPage.tsx";
// Put any other imports below so that CSS from your
// components takes precedence over default styles.



const router = createBrowserRouter([
    {
        path: "/",
        element: <LoginPage/>,
    },
    {
        path: "/chat",
        element: <App/>,
    },
    {
        path: "/task",
        element: <TrackerView/>,
    },
    {
        path: "/home",
        element: <Main/>,
    },
    {
        path: "/success",
        element: <SuccessPage/>,
    },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
      <Provider store={store}>
          <RouterProvider router={router} />
      </Provider>
  </React.StrictMode>,
)
