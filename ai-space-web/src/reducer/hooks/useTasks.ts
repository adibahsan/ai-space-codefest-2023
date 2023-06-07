import { useDispatch, useSelector } from 'react-redux';

import { useCallback } from 'react';
import {RootState} from "../rootReducer.ts";
import {Tasks} from "../../api/api";
import {setTasks} from "../taskReducer.ts";

const useTasks = () => {
    const dispatch = useDispatch();
    const tasks = useSelector((state: RootState) => state.taskState);

    const updateTasks = useCallback((newTasks: Tasks[]) => {
        dispatch(setTasks(newTasks));
    }, [dispatch]);

    return [ tasks, updateTasks ];
};

export default useTasks;
