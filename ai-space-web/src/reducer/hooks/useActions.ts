import { useDispatch, useSelector } from 'react-redux';

import { useCallback } from 'react';
import {RootState} from "../rootReducer.ts";
import {setActions} from "../actionReducer.ts";
import {Actions} from "../../api/api";

const useActions = () => {
    const dispatch = useDispatch();
    const actions = useSelector((state: RootState) => state.actionState);

    const updateActions = useCallback((newActions: Actions[]) => {
        dispatch(setActions(newActions));
    }, [dispatch]);

    const handleActionToggle = (taskId: string) => {
        const updatedTasks = actions.actions.map((task) => {
            if (task.id === taskId) {
                return {
                    ...task,
                    status: !task.status,
                };
            }
            return task;
        });

        dispatch(setActions(updatedTasks));
    }

    return  [actions, updateActions, handleActionToggle];
};

export default useActions;
