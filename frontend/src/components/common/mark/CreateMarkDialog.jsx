import React, {useEffect, useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import MarkService from "../../../services/MarkService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

function CreateMarkDialog({ subjectId, studentId, open, onClose }) {
    const [newMark, setNewMark] = useState('');
    const [errorMessages, setErrorMessages] = useState([]);

    useEffect(() => {
        setErrorMessages([]);
    }, [open]);

    const handleCreateMark = async () => {
        try {
            const createdMark = await MarkService.create(
                { value: newMark },
                subjectId,
                studentId,
                localStorage.getItem('jwtToken')
            );
            setNewMark('');
            onClose(studentId, createdMark);
        } catch (error) {
            if (error.response && error.response.data.messages && Array.isArray(error.response.data.messages)) {
                setErrorMessages(error.response.data.messages);
            } else {
                console.error("Error updating subject:", error);
            }
        }
    };

    const handleChange = (event) => {
        setNewMark(event.target.value);
    };

    return (
        <Dialog onClose={onClose} open={open} maxWidth="xs" fullWidth>
            <DialogTitle>Create Mark</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="newMark"
                    label="Mark Value"
                    fullWidth
                    value={newMark}
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
                    <Button variant="contained" onClick={handleCreateMark} color="success">
                        Create
                    </Button>
                </Box>
            </DialogContent>
        </Dialog>
    );
}

export default CreateMarkDialog;
