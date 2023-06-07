export interface Tasks {
    id:string;
    status: boolean;
    taskName: string | null;
}

export interface Actions {
    id:string;
    status: boolean;
    actionName: string | null;
}

export interface Message {
    role: string;
    content: string;
}

export interface PayLoad {
    tasks: Tasks[] | null;
    actions: Actions[] | null;
}

export interface ChatResp {
    message: Message;
    payLoad: PayLoad | null;
}
