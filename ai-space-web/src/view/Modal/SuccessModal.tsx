import React, {useEffect, useState} from 'react';
import './SuccessModal.css';
import useTasks from "../../reducer/hooks/useTasks.ts";

interface SuccessModalProps {
    isOpen: boolean;
    onClose?: () => void;
}

const SuccessModal = () => {


    const [isOpen, setIsOpen] = useState(false);

    const [taskList, , ] = useTasks()

    const filteredTasks = taskList?.tasks?.filter(it => !it.status).length === 0 && taskList?.tasks?.length > 0




    function onClose(){
        setIsOpen(true)
        window.location.href="/success"
    }

    return (
        <>
            {filteredTasks && !isOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <img src="../../../public/success.jpg" alt="Success" />
                        <h2>Your Onboarding is Completed!! </h2>
                        <h3>Welcome to the Team </h3>
                        <br/>
                        <button className={"btn btn-primary"} onClick={onClose}>Continue</button>
                    </div>
                </div>
            )}
        </>
    );
};

export default SuccessModal;
