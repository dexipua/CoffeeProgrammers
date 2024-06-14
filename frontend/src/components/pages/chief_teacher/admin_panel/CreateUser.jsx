import React, {useState} from 'react';
import StudentService from '../../../../services/StudentService';
import TeacherService from '../../../../services/TeacherService';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";
import FormControlLabel from "@mui/material/FormControlLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";

const CreateUser = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessages, setErrorMessages] = useState([]);


    const handleCreateUser = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        try {
            const token = localStorage.getItem('jwtToken');

            if (role === 'STUDENT') {
                await StudentService.create({ firstName, lastName, email, password }, token);
            } else {
                await TeacherService.create({ firstName, lastName, email, password }, token);
            }

            setErrorMessages([])
            setFirstName('');
            setLastName('');
            setEmail('');
            setPassword('');
        } catch (error) {
            if (error.response && error.response.data.messages && Array.isArray(error.response.data.messages)) {
                const errorMessages = error.response.data.messages;
                setErrorMessages(errorMessages);
            } else {
                console.error("Error creating user:", error);
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Box
            width={400}
            height="auto"
            my={2}
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            gap={1}
            p={2}
            sx={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
            }}
        >
            <Typography variant="h6" gutterBottom>
                Create User
            </Typography>

            <form onSubmit={handleCreateUser} style={{ width: '100%' }}>
                <TextField
                    fullWidth
                    variant="outlined"
                    label="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    required
                    sx={{ mb: 1 }}
                />
                <TextField
                    fullWidth
                    variant="outlined"
                    label="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    required
                    sx={{ mb: 1 }}
                />
                <TextField
                    fullWidth
                    variant="outlined"
                    type="email"
                    label="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    sx={{ mb: 1 }}
                />
                <TextField
                    fullWidth
                    variant="outlined"
                    type="password"
                    label="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    sx={{ mb: 1 }}
                />
                {errorMessages.length > 0 && (
                    <Box mt={2}>
                        {errorMessages.map((message, index) => (
                            <Typography key={index} color="error">{message}</Typography>
                        ))}
                    </Box>
                )}

                <RadioGroup
                    row
                    aria-label="role"
                    name="role"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    sx={{
                        display: 'flex',
                        justifyContent: 'center',
                        width: '100%',
                    }}
                >
                    <FormControlLabel value="STUDENT" control={<Radio />} label="Student" />
                    <FormControlLabel value="TEACHER" control={<Radio />} label="Teacher" />
                </RadioGroup>

                {isLoading ? (
                    <Button variant="contained" color="primary" disabled sx={{ mb: 2 }}>
                        <CircularProgress size={24} />
                    </Button>
                ) : (
                    <Button
                        disabled={role === null}
                        variant="contained" color="primary" type="submit" sx={{ mb: 2 }}>
                        Create
                    </Button>
                )}
            </form>
        </Box>
    );
};

export default CreateUser;
