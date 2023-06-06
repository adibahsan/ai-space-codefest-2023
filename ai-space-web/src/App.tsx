// import React, {ChangeEvent, useState} from "react";
import "./App.css";
import Main from "./view/Main.tsx";
import Header from "./view/Navbar/Header.tsx";

export type ChatMessage = {
    role: string;
    content: string;
}



function App(): JSX.Element {
    return (
        <>
            <Header />
            <Main/>
        </>

    )
}
export default App;
