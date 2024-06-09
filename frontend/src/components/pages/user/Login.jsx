import React, {useState} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import '../../../assets/styles/Login.css';


const PASSWORD = "passWord1"; //TODO: better for testing

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

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
            localStorage.setItem('role', response.data.role)
            navigate("/home");
        } catch (error) {
            setError('Login failed. Check your credentials.');
            console.error('Login failed. Check your credentials.');
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
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(PASSWORD)} // Note: For testing, replace with e.target.value in production
                />
                <button type="submit">Login</button>
            </form>
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default Login;
