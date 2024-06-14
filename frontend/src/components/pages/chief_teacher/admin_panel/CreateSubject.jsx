import React, {useState} from 'react';
import SubjectService from '../../../../services/SubjectService';
import {Box, Button, CircularProgress, TextField, Typography} from '@mui/material';


const CreateSubject = () => {
    const [name, setName] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessages, setErrorMessages] = useState([]);

    const handleCreateSubject = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setErrorMessages([])
        try {
            const token = localStorage.getItem('jwtToken');
            await SubjectService.create({ name }, token);
            setName('');
        } catch (error) {
            if (error.response && error.response.data.messages && Array.isArray(error.response.data.messages)) {
                const errorMessages = error.response.data.messages;
                setErrorMessages(errorMessages);
            } else {
                console.error("Error updating subject:", error);
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
                Create Subject
            </Typography>
            <form onSubmit={handleCreateSubject} style={{ width: '100%' }}>
                <TextField
                    fullWidth
                    variant="outlined"
                    label="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                {errorMessages.length > 0 && (
                    <Box mt={2}>
                        {errorMessages.map((message, index) => (
                            <Typography key={index} color="error">{message}</Typography>
                        ))}
                    </Box>
                )}
                <Box mt={2}>
                    {isLoading ? (
                        <Button variant="contained" color="primary" disabled>
                            <CircularProgress size={24} />
                        </Button>
                    ) : (
                        <Button variant="contained" color="primary" type="submit">
                            Create
                        </Button>
                    )}
                </Box>
            </form>
        </Box>
    );
};

export default CreateSubject;
