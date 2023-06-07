import React, {useEffect, useRef, useState} from "react";
import {FormEvent} from "react/ts5.0";
import {ChatMessage} from "../App";
import {ChatResp, Message} from "../api";

function ChatView():JSX.Element {

    const [message, setMessage] = useState<string>("");
    const [chats, setChats] = useState<Message[]>([]);
    const [isTyping, setIsTyping] = useState<boolean>(false);
    const [isError, setIsError] = useState<boolean>(false);
    const chatContainerRef = useRef(null);


    useEffect(()=>{
        console.log("New Chat")
        scrollChatToBottom()
        // bottomRef.current?.scrollIntoView({behavior: 'smooth'});
    },[chats])


    const scrollChatToBottom = () => {
        if (chatContainerRef.current) {
            console.log("Scrolling")
            chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
        }
    };


    console.log("Chats", chats)

    const chatUpdater = async (
        e: FormEvent<HTMLFormElement>,
        message: string
    ): Promise<void> => {
        e.preventDefault();
        console.log("E Value", e.target);
        scrollChatToBottom()
        if (!message) return;
        setIsTyping(true);
        window.scrollTo(0, 1e10);

        const msgs: ChatMessage[] = [...chats];
        msgs.push({ role: "user", content: message });
        setChats(msgs);
        const request: ChatMessage = { role: "user", content: message };
        // console.log("MSG_LIst", msgs);
        // console.log("Message", message);

        setMessage("");

        fetch("http://localhost:8080/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                role: request.role,
                content: request.content,
            }),
        })
            .then((response) => {
                setIsError(!response.ok)
                if (!response.ok) {
                    setIsTyping(false)
                    throw new Error("Error: " + response.status);
                }
                return response.json();
            })
            .then((data: ChatResp) => {
                console.log("ChatResp", data)
                msgs.push(data.message);
                setChats(msgs);
                setIsTyping(false);
                window.scrollTo(0, 1e10);
            })
            .catch((error) => {
                console.log("Fetch error:", error);
                // Handle the error here, e.g., show an error message to the user
            });
    };

    return (
        <div className={"card"}>
            <h5 style={{backgroundColor:"black", color:"white", borderRadius:"20px", position:"sticky"}}>AI Assistant</h5>
            <div className={"chat-container"}  >
                {chats && chats.length
                    ? chats.map((chat: ChatMessage, index: number) => (
                        <>
                            <div className={`${chat.role === "user" ? "user_msg_div": "ai_msg_div"} d-flex`}>
                            <small style={{fontWeight:"bold"}} >{chat.role ==="user" ? "User" : "Assistant"}</small>
                            </div>
                            <p key={index} className={chat.role === "user" ? "user_msg" : "ai_msg"}>
                                <pre>{chat.content}</pre>
                            </p>
                        </>
                    ))
                    : ""}

                <div id={"NullDiv"} ref={chatContainerRef} />
            </div>

            <div className={isTyping ? "" : "hide"}>
                <text>
                    <i>{isTyping ? "Assistant is Typing" : ""}</i>
                </text>
            </div>

            <div className={isError ? "" : "hide"}>
                <text style={{color:"red"}}>
                    <i>{isError ? "Error Occured, Please try again" : ""}</i>
                </text>
            </div>



            <form action="" onSubmit={(e) => chatUpdater(e, message)}>
                <input
                    type="text"
                    name="message"
                    value={message}
                    placeholder="Type a message here and hit Enter..."
                    onChange={(e) => setMessage(e.target.value)}
                />
            </form>
        </div>
    );
}

export default ChatView
