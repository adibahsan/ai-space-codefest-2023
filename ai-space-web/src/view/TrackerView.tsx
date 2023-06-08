import React, {useEffect, useState} from "react";
import useTasks from "../reducer/hooks/useTasks.ts";
import {Tasks} from "../api/api";


function TrackerView() {
    const [taskList, , updateTaskList] = useTasks()
    const [filterCount, setFilterCount] = useState<number>(0);

    useEffect(() => {
        setFilterCount(taskList?.tasks?.filter(it => !it.status) ?? 0)
    }, [taskList]);

    var isChecked = (item: boolean) =>
        item ? "checked-item" : "not-checked-item";

    function check(id: string) {
        fetch("http://localhost:8080/v1/task/check/" + id, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then((response) => {
                return response.json();
            })
    }

    function uncheck(id: string) {
        fetch("http://localhost:8080/v1/task/uncheck/" + id, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then((response) => {
                return response.json();
            })
    }

    return (
        <div className={"card"}>
            <h5 className="title" style={{backgroundColor:"#5b0000", color:"#f5ffa5", borderRadius: "30px"}}>Your CheckList
            </h5>
            <div className="list-container">
                {taskList.tasks.length === 0 &&

                    <>
                    <h6>You Currently don't have any pending task</h6>
                    </>}
                {taskList?.tasks.map((task: Tasks, index: number) => (
                    <div className={"row"} key={index}>
                        <div className={"col-sm-3 d-flex align-items-center"}>
                            <input
                                type="checkbox"
                                checked={task.status}
                                style={{width: "20px"}}
                                onChange={event => {
                                    updateTaskList(task.id)
                                    if (event.target.checked) {
                                        check(task.id)
                                    } else {
                                        uncheck(task.id)
                                    }

                                }}
                            />
                        </div>
                        <div className={"col-sm-6 d-flex align-items-center"}>
                            <small className="" style={{textAlign: "left"}}>{task.taskName}</small>
                        </div>


                    </div>
                ))}
            </div>
        </div>
    );
}

export default TrackerView
