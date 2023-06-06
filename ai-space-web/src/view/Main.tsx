import ChatView from "./ChatView";
import TrackerView from "./TrackerView";

function Main():JSX.Element {
    return (
        <>
            <div className={"row"}>
                <div className={"col-sm-12"}>
                    <h1>OnBoarding AI Space</h1>
                    <br/>
                </div>
                <div className={"col-sm-3"}>
                    <TrackerView/>
                </div>
                <div className={"col-sm-6"}>
                    <ChatView/>
                </div>

                <div className={"col-sm-3"}>
                    <TrackerView/>
                </div>
            </div>
        </>
    )
}

export default Main
