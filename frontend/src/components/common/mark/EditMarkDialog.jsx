import React, {useEffect, useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import MarkService from "../../../services/MarkService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

function EditMarkDialog({markId, open, onClose, onMarkUpdate}) {
    const [newMark, setNewMark] = useState('');
    const [errorMessages, setErrorMessages] = useState([]);

    useEffect(() => {
        setErrorMessages([]);
    }, [open]);

    const handleAccept = async () => {
        try {
            const updatedMark = await MarkService.update(
                markId,
                {value: newMark},
                localStorage.getItem('jwtToken'))
            onMarkUpdate(updatedMark)
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
        setNewMark(event.target.value);
    };

    return (
        <Dialog onClose={onClose} open={open} maxWidth="xs" fullWidth>
            <DialogTitle>Edit Mark</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="newMark"
                    label="New Mark"
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
                <div style={{display: 'flex', justifyContent: 'center', marginTop: '16px'}}>
                    <Button variant="contained" onClick={onClose} style={{marginRight: '8px'}} color="error">
                        Cancel
                    </Button>
                    <Button variant="contained" onClick={handleAccept} color="success">
                        Accept
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}

export default EditMarkDialog;