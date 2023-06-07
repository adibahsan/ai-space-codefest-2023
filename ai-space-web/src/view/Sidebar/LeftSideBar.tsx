import React from "react";
import TrackerView from "../TrackerView.tsx";

const LeftSideBar = props => {
    const sidebarClass = props.isOpen ? "sidebar open" : "sidebar";
    return (
        <div className={sidebarClass}>
            <TrackerView/>
            <button onClick={props.toggleSidebar} className="sidebar-toggle">
                Actions
            </button>
        </div>
    );
};
export default LeftSideBar;
