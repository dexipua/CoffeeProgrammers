import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import '../../../assets/styles/Login.css';
import UserService from "../../../services/UserService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [errorMessages, setErrorMessages] = useState([]);

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setErrorMessages([])
        try {
            await UserService.login(username, password);
            navigate("/home");
        } catch (error) {
            if (error.response && error.response.data.messages && Array.isArray(error.response.data.messages)) {
                const errorMessages = error.response.data.messages;
                setErrorMessages(errorMessages);
            } else {
                console.error("Error login:", error);
            }
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
                    required
                    type="text"
                    placeholder="Email"
                    value={username}
                    onChange={(e) =>
                        setUsername(e.target.value)}
                />
                <input
                    required
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)}
                />
                {errorMessages.length > 0 && (
                    <Box mt={2}>
                        {errorMessages.map((message, index) => (
                            <Typography key={index} color="error">{message}</Typography>
                        ))}
                    </Box>
                )}
                <button type="submit">Login</button>
            </form>
        </Box>
    );
};

export default Login;
