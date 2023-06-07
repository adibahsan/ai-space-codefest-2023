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

    return  [actions, updateActions];
};

export default useActions;
