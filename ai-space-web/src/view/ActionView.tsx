import React, { useState } from "react";
import {Actions, Tasks} from "../api/api";
import useActions from "../reducer/hooks/useActions.ts";
import {Button} from "react-bootstrap";


function ActionView() {
    // State with list of all checked item
    const [actionList, , handleActions] = useActions()

    return (
        <div className={"card"} >
                <h4 style={{backgroundColor:"purple", color:"white", borderRadius: "10px"}}>Pending Actions:</h4>
                <ul>
                    {actionList?.actions.map((action:Actions, index:number) => (
                        <ActionPad action={action}/>
                        // <li key={index}>
                        //     <label>
                        //         <input
                        //             type="checkbox"
                        //             checked={action.status}
                        //             onChange={() => handleActions(action.id)}
                        //         />
                        //         {action.actionName}
                        //     </label>
                        // </li>
                    ))}
                </ul>
        </div>
    );
}

interface ActionPadProps {
    action: Actions;
}

function ActionPad({action}: ActionPadProps) {
    const [, , handleActions] = useActions()


    return (
        <>
            <div className={"w-100 d-flex row flex-direction-row"}
                 style={{backgroundColor: action.status ? "pink" : "grey", borderRadius: "10px"}}>
                <div className={"col-sm-12 bg-black"}
                     style={{color: action.status ? "wheat" : "darkgray", borderRadius: "10px"}}>
                    <text style={{textDecoration: action.status ? "" : "line-through"}}>{action.actionName}</text>
                </div>
                <br/>
                <div className={"col-sm-6"}>
                    <Button disabled={!action.status} className={"btn btn-success w-100 mt-1 mb-1"}
                            onClick={()=>{
                                handleActions(action.id, true)
                            }}
                            style={{textDecoration: action.status ? "" : "line-through"}}>yes</Button>
                </div>
                <div className={"col-sm-6"}>
                    <Button disabled={!action.status} className={"btn btn-danger w-100 mt-1 mb-1"}
                            onClick={()=>{
                                handleActions(action.id, false)
                            }}
                            style={{textDecoration: action.status ? "" : "line-through"}}>no</Button>
                </div>
            </div>
            <br/>
        </>
    )
}
export default ActionView
