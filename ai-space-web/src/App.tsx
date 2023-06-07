// import React, {ChangeEvent, useState} from "react";
import "./App.css";
import Main from "./view/Main.tsx";
import Header from "./view/Navbar/Header.tsx";
import {useState} from "react";
import LeftSideBar from "./view/Sidebar/LeftSideBar.tsx";
import RightSideBar from "./view/Sidebar/RightSideBar.tsx";

export type ChatMessage = {
    role: string;
    content: string;
}



function App(): JSX.Element {
    const [leftbarOpen, setLeftBarOpen] = useState(false);
    const [rightbarOpen, setRightBarOpen] = useState(false);
    const handleViewSidebar = () => {
        setLeftBarOpen(!leftbarOpen);
    }
    const handleViewRightbar = () => {
        setRightBarOpen(!rightbarOpen);
    }
    return (
        <>
            <Header />
            {/*<LeftSideBar isOpen={sidebarOpen} toggleSidebar={handleViewSidebar} />*/}
            <br/>
            <RightSideBar isOpen={rightbarOpen} toggleSidebar={handleViewRightbar} />
            <LeftSideBar isOpen={leftbarOpen} toggleSidebar={handleViewSidebar} />
            <Main/>
        </>

    )
}
export default App;
