import './LoginPage.css';
import colors from "cdbreact/dist/theme/colors";
import {useEffect, useState} from "react";
const SuccessPage = () => {

    const [username, setUsername] = useState('');
    useEffect(() => {
        localStorage.clear()
    }, []);

    const handleInputChange = (event) => {
        setUsername(event.target.value);
    };
    return (
        <div className="login-page">
            <div className="login-page">
                <div>
                    <h1 className={"login-main-heading"}>Thank You</h1>
                    <pre style={{color:"#c8dd1b"}}>For Using AI Space</pre>
                    <img src="../../../public/logo1.png" alt="Logo" className="logo" />
                    <h1 className={"login-main-heading"}>Welcome to HMS</h1>
                </div>
            </div>
        </div>
    );
};

export default SuccessPage;
