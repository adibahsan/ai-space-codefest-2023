import React from "react";
import ActionView from "../ActionView.tsx";
import useActions from "../../reducer/hooks/useActions.ts";

const LeftSideBar = props => {
    const sidebarClass = props.isOpen ? "sidebar open" : "sidebar";
    const [actionList, ,] = useActions()


    return (
        <div className={sidebarClass}>
            <ActionView/>
            <button onClick={props.toggleSidebar} className="sidebar-toggle">
                Actions
                {actionList?.actions?.length > 0 && <span style={{left: "12px"}} className="notification-counter">{actionList?.actions?.length ?? 0}</span>}
            </button>
        </div>
    );
};
export default LeftSideBar;
