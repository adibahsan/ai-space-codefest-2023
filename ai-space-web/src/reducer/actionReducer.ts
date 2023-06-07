import {Actions} from "../api/api";


export interface ActionsState {
    actions: Actions[];
}

const initialState: ActionsState = {
    actions: [],
};
// Define action types
interface SetActionsAction {
    type: 'SET_ACTIONS';
    payload: Actions[];
}

// Define action creators
export const setActions = (actions: Actions[]): SetActionsAction => {
    return { type: 'SET_ACTIONS', payload: actions };
};

const actionsReducer = (state = initialState, action: any): ActionsState => {
    switch (action.type) {
        case 'SET_ACTIONS':
            return { ...state, actions: action.payload };
        // Add other action-related actions if needed
        default:
            return state;
    }
};

export default actionsReducer;
