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
// Put any other imports below so that CSS from your
// components takes precedence over default styles.

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
    },
    {
        path: "/chat",
        element: <ChatView/>,
    },
    {
        path: "/task",
        element: <TrackerView/>,
    },
    {
        path: "/home",
        element: <Main/>,
    },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>,
)
