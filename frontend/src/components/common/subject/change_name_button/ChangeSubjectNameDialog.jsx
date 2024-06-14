import React, {useEffect, useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import SubjectService from "../../../../services/SubjectService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

function ChangeSubjectNameDialog({ subjectId, open, onClose, onNameChange }) {
    const [newSubjectName, setNewSubjectName] = useState('');
    const [errorMessages, setErrorMessages] = useState([]);

    useEffect(() => {
        setErrorMessages([]);
    }, [open]);

    const handleAccept = async () => {
        try {
            await SubjectService.update(
                subjectId,
                { name: newSubjectName },
                localStorage.getItem('jwtToken')
            );
            onNameChange(newSubjectName);
            setNewSubjectName('')
            onClose();
        } catch (error) {
            if (error.response && error.response.data.messages && Array.isArray(error.response.data.messages)) {
                const errorMessages = error.response.data.messages;
                setErrorMessages(errorMessages);
            } else {
                console.error("Error updating subject:", error);
            }
        }

    };

    const handleChange = (event) => {
        setNewSubjectName(event.target.value);
    };

    return (
        <Dialog onClose={onClose} open={open} maxWidth="xs" fullWidth>
            <DialogTitle>Edit subject name</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="newMark"
                    label="New name"
                    fullWidth
                    value={newSubjectName}
                    onChange={handleChange}
                />
                {errorMessages.length > 0 && (
                    <Box mt={2}>
                        {errorMessages.map((message, index) => (
                            <Typography key={index} color="error">{message}</Typography>
                        ))}
                    </Box>
                )}
                <Box mt={2} display="flex" justifyContent="center">
                    <Button variant="contained" onClick={onClose} sx={{ mr: 1 }} color="error">
                        Cancel
                    </Button>
                    <Button variant="contained" onClick={handleAccept} color="success">
                        Accept
                    </Button>
                </Box>
            </DialogContent>
        </Dialog>
    );
}

export default ChangeSubjectNameDialog;
