import React, { useState } from "react";
import useTasks from "../reducer/hooks/useTasks.ts";
import {Tasks} from "../api/api";


function TrackerView() {
    // State with list of all checked item
    const [taskList, setTaskList, updateTaskList] = useTasks()
    const [checked, setChecked] = useState([]);
    

    return (
        <div className={"card"} >
            <div>
                <h2>Tasks:</h2>
                <ul>
                    {taskList?.tasks.map((task:Tasks, index:number) => (
                        <li key={index}>
                            <label>
                                <input
                                    type="checkbox"
                                    checked={task.status}
                                    onChange={() => updateTaskList(task.id)}
                                />
                                {task.taskName}
                            </label>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default TrackerView
