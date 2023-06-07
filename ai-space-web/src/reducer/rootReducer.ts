import {combineReducers, Reducer} from 'redux';
import tasksReducer, {TasksState} from "./taskReducer.ts";
import actionsReducer, {ActionsState} from "./actionReducer.ts";


export interface RootState{
    taskState : TasksState,
    actionState: ActionsState
}

const rootReducer:Reducer<RootState, any> = combineReducers({
    taskState: tasksReducer,
    actionState: actionsReducer
});



export default rootReducer;
