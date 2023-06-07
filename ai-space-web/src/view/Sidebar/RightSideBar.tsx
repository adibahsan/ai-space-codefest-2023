import React from "react";
import TrackerView from "../TrackerView.tsx";

const RightSideBar = props => {
    const sidebarClass = props.isOpen ? "sidebar-right open" : "sidebar-right";
    return (
        <div className={sidebarClass}>
            <TrackerView/>
            <button onClick={props.toggleSidebar} className="sidebar-toggle-right">
                Tasks
            </button>
        </div>
    );
};
export default RightSideBar;
