import React, {useState} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import '../../../assets/styles/Login.css';

const EMAIL_CHIEF_TEACHER = "am@gmail.com"; //TODO: better for testing
const EMAIL_FIRST_STUDENT = "vk@gmail.com"; //TODO: better for testing

const PASSWORD = "passWord1"; //TODO: better for testing

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    let navigate = useNavigate();

    const handleLogin = async (e) => { //TODO
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:9091/api/auth/login', {
                username,
                password,
            });

            const jwtToken = response.data.accessToken;

            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('role', response.data.role)
            navigate("/home");
        } catch (error) {
            console.error('Login failed. Check your credentials.');
            navigate("");
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Email"
                    value={username}
                    onChange={(e) =>
                        //setUsername(e.target.value)}
                        setUsername(EMAIL_FIRST_STUDENT)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        //setPassword(e.target.value)}
                        setPassword(PASSWORD)}
                />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
