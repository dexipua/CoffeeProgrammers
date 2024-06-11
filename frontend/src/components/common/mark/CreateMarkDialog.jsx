import React, {useEffect, useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import MarkService from "../../../services/MarkService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

function CreateMarkDialog({subjectId, studentId, open, onClose }) {
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
            setNewMark('')
            console.log("CreatedMark", studentId,  createdMark)
            onClose(studentId, createdMark);
        } catch (error) {
            if (error.response && error.response.data && Array.isArray(error.response.data)) {
                const errorMessages = error.response.data.map(errorObj => errorObj.message);
                setErrorMessages(errorMessages);
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
                <div style={{ display: 'flex', justifyContent: 'center', marginTop: '16px' }}>
                    <Button variant="contained" onClick={onClose} style={{ marginRight: '8px' }} color="error">
                        Cancel
                    </Button>
                    <Button variant="contained" onClick={handleCreateMark} color="success">
                        Create
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}

export default CreateMarkDialog;
