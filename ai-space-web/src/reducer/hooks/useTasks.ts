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

    const handleTaskToggle = (taskId: string) => {
        const updatedTasks = tasks.tasks.map((task) => {
            if (task.id === taskId) {
                return {
                    ...task,
                    status: !task.status,
                };
            }
            return task;
        });

        dispatch(setTasks(updatedTasks));
    }

    return [ tasks, updateTasks, handleTaskToggle ];
};

export default useTasks;
