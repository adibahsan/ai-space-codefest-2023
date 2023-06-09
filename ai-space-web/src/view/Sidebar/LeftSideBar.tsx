import React from "react";
import ActionView from "../ActionView.tsx";
import useActions from "../../reducer/hooks/useActions.ts";

const LeftSideBar = props => {
    const sidebarClass = props.isOpen ? "sidebar open" : "sidebar";
    const [actionList, ,] = useActions()

    const filteredTasks = actionList?.actions?.filter(it => it.status).length


    return (
        <div className={sidebarClass}>
            <ActionView/>
            <button onClick={props.toggleSidebar} className="sidebar-toggle">
                Actions
                {filteredTasks > 0 && <span className="notification-counter">{filteredTasks ?? 0}</span>}
            </button>
        </div>
    );
};
export default LeftSideBar;
