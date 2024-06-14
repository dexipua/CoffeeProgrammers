import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import '../../../assets/styles/Login.css';
import UserService from "../../../services/UserService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";


const PASSWORD = "passWord1"; //TODO: better for testing

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            await UserService.login(username, password);
            navigate("/home");
        } catch (error) {
            console.error('Login failed. Check your credentials.');
        }
    };

    return (
        <Box className="login-container">
            <Box sx={{textAlign: 'center'}}>
                <Typography variant="h5" sx={{
                    fontSize: '24px',
                    marginBottom: '20px',
                    color: '#333'
                }}
                >
                    Login
                </Typography>
            </Box>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Email"
                    value={username}
                    onChange={(e) =>
                        setUsername(e.target.value)}
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
        </Box>
    );
};

export default Login;
