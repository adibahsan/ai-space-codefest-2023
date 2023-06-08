import React from "react";
import TrackerView from "../TrackerView.tsx";
import useTasks from "../../reducer/hooks/useTasks.ts";

const RightSideBar = props => {
    const sidebarClass = props.isOpen ? "sidebar-right open" : "sidebar-right";
    const [taskList, , updateTaskList] = useTasks()


    let filteredTasks = taskList?.tasks?.filter(it => !it.status)

    console.log("TaskList", taskList, filteredTasks)

    return (
        <div className={sidebarClass}>
            <TrackerView/>
            <button onClick={props.toggleSidebar} className="sidebar-toggle-right">
                Tasks
                {filteredTasks?.length > 0 && <span className="task-notification-counter">{filteredTasks?.length ?? 0}</span>}
            </button>
        </div>
    );
};
export default RightSideBar;
