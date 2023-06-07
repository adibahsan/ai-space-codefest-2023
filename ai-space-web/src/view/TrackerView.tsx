import React, { useState } from "react";
import useTasks from "../reducer/hooks/useTasks.ts";
import {Tasks} from "../api/api";


function TrackerView() {
    const [taskList, , updateTaskList] = useTasks()

    var isChecked = (item: boolean) =>
        item ? "checked-item" : "not-checked-item";

    return (
        <div className={"card"} >
                <div className="title" style={{backgroundColor:"black", color:"white", borderRadius:"10px"}}>Your CheckList:</div>
                <div className="list-container">
                    {taskList?.tasks.map((task:Tasks, index:number) => (
                        <div className={"row"} key={index}>
                            <div className={"col-sm-3 d-flex"}>
                                <input
                                    type="checkbox"
                                    checked={task.status}
                                    style={{width:"20px"}}
                                    onChange={() => updateTaskList(task.id)}
                                />
                            </div>
                            <div className={"col-sm-6 d-flex"}>
                                <small className="" style={{textAlign:"left"}}>{task.taskName}</small>
                            </div>


                        </div>
                    ))}
                </div>
        </div>
    );
}

export default TrackerView
