import ChatView from "./ChatView";
import TrackerView from "./TrackerView";
import FloatingButton from "./ButtonDirectory/FloatingButton.tsx";

function Main():JSX.Element {
    return (
        <>
            {/*<NavbarComponent/>*/}
            <div className={"row"}>
                <div className={"col-sm-2"}/>
                {/*    /!*<TrackerView/>*!/*/}
                {/*</div>*/}
                <div className={"col-sm-8"}>
                    <ChatView/>
                </div>
                {/*<FloatingButton/>*/}

                {/*<div className={"col-sm-3"}>*/}
                {/*    <TrackerView/>*/}
                {/*</div>*/}
            </div>
        </>
    )
}

export default Main
