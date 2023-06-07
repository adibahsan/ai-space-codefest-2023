import {Tasks} from "../api/api";


export interface TasksState {
    tasks: Tasks[];
}

const initialState: TasksState = {
    tasks: [],
};

// Define action types
interface SetTasksAction {
    type: 'SET_TASKS';
    payload: Tasks[];
}

// Define action creators
export const setTasks = (tasks: Tasks[]): SetTasksAction => {
    return { type: 'SET_TASKS', payload: tasks };
};


const tasksReducer = (state = initialState, action: any): TasksState => {
    switch (action.type) {
        case 'SET_TASKS':
            return { ...state, tasks: action.payload };
        // Add other task-related actions if needed
        default:
            return state;
    }
};

export default tasksReducer;
