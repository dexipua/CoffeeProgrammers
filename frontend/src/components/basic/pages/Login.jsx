import React, {useState} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const EMAIL1 = "am@gmail.com"; //TODO: better for testing
const PASSWORD1 = "passWord1"; //TODO: better for testing
const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    let navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:9091/api/auth/login', {
                username,
                password,
            });

            const jwtToken = response.data.accessToken;
            localStorage.setItem('jwtToken', jwtToken);
            navigate("/home");
        } catch (error) {
            console.error('Login failed. Check your credentials.');
            navigate("");
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) =>
                        //setUsername(e.target.value)} TODO: better for testing
                        setUsername(EMAIL1)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        //setPassword(e.target.value)} TODO: better for testing
                        setPassword(PASSWORD1)}
                />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
