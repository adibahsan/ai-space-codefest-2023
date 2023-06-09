import React, {useState} from "react";
import {Actions, Tasks} from "../api/api";
import useActions from "../reducer/hooks/useActions.ts";
import {Button} from "react-bootstrap";

function ActionView() {
    // State with list of all checked item
    const [actionList, ,] = useActions()

    return (
        <div className={"card"}>
            <h5 className="title" style={{backgroundColor:"#5b0000", color:"#f5ffa5", borderRadius: "30px"}}>Pending
                Actions
            </h5>
            {actionList.actions.length === 0 &&

                <>
                    <h6>You Currently don't have any pending Actions</h6>
                </>}
            <div>
                {actionList?.actions.map((action: Actions, index: number) => (
                    <ActionPad key={index} action={action}/>
                ))}
            </div>
        </div>
    );
}

interface ActionPadProps {
    action: Actions;
}

function approve(id:string) {
    fetch("http://localhost:8080/v1/action/approve/" + id, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then((response) => {
            return response.json();
        })
}

function reject(id:string) {
    fetch("http://localhost:8080/v1/action/reject/" + id, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then((response) => {
            return response.json();
        })
}

function ActionPad({action}: ActionPadProps) {
    const [, , handleActions] = useActions()

    return (
        <>
            <div className={"w-100 d-flex row flex-direction-row pb-1"}
                 style={{backgroundColor: action.status ? "#25194e" : "grey", borderRadius: "10px", fontSize: "0.85rem"}}>
                <div className={"col-sm-12 bg-black"}
                     style={{color: action.status ? "#c8dd1b" : "darkgray", borderRadius: "10px"}}>
                    <small style={{textDecoration: action.status ? "" : "line-through"}}>{action.actionName}</small>
                </div>
                <br/>
                <div className={"col-sm-6"}>
                    <Button disabled={!action.status} className={"btn btn-success w-100 mt-1 mb-1"}
                            onClick={() => {
                                approve(action.id)
                                handleActions(action.id, false)

                            }}
                            style={{textDecoration: action.status ? "" : "line-through"}}><small>yes</small></Button>
                </div>
                <div className={"col-sm-6"}>
                    <Button disabled={!action.status} className={"btn btn-danger w-100 mt-1 mb-1"}
                            onClick={() => {
                                reject(action.id)
                                handleActions(action.id, false)
                            }}
                            style={{textDecoration: action.status ? "" : "line-through"}}><small>no</small></Button>
                </div>
            </div>
            <br/>
        </>
    )
}

export default ActionView
