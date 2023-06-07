import React, { useState } from "react";
import useTasks from "../reducer/hooks/useTasks.ts";
import {Actions, Tasks} from "../api/api";
import useActions from "../reducer/hooks/useActions.ts";


function ActionView() {
    // State with list of all checked item
    const [actionList, , handleActions] = useActions()

    return (
        <div className={"card"} >
                <h2>Pending Actions:</h2>
                <ul>
                    {actionList?.actions.map((action:Actions, index:number) => (
                        <li key={index}>
                            <label>
                                <input
                                    type="checkbox"
                                    checked={action.status}
                                    onChange={() => handleActions(action.id)}
                                />
                                {action.actionName}
                            </label>
                        </li>
                    ))}
                </ul>
        </div>
    );
}

export default ActionView
