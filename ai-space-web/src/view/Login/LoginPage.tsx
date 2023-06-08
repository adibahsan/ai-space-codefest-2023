import './LoginPage.css';
import {useEffect, useState} from "react";
const LoginPage = () => {

    const [username, setUsername] = useState('');

    const clickHandler = () => {
        window.location.href = '/chat';
        localStorage.setItem('USER_NAME_AI', username);
    };

    useEffect(() => {
        localStorage.clear()
    }, []);

    const handleInputChange = (event) => {
        setUsername(event.target.value);
    };
    return (
        <div className="login-page">
                <div>
                    <h1 className={"login-main-heading"}>AI SPACE</h1>
                    <pre style={{color:"#c8dd1b"}}>“Simplify Onboarding, Amplify Productivity”</pre>
                    <img src="../../../public/logo1.png" alt="Logo" className="logo" />
                    <div className="form-container">
                        <input
                            type="text"
                            placeholder="Enter your username"
                            className="input"
                            value={username}
                            onChange={handleInputChange}
                        />
                        <button className="button" onClick={clickHandler}>Go</button>
                    </div>
                </div>
        </div>
    );
};

export default LoginPage;
