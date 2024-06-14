import React, {useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SchoolNewsService from "../../../../services/SchoolNewsService";

function SchoolNewsCreateDialog({open, onClose, onCreate}) {
    const [text, setText] = useState();
    const [errorMessages, setErrorMessages] = useState([]);

    const handleAccept = async () => {
        try {
            const createdNews = await SchoolNewsService.create(
                {text: text},
                localStorage.getItem('jwtToken'))
            onCreate(createdNews);
            setErrorMessages([])
            setText('');
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
        setText(event.target.value);
    };

    return (

        <Dialog onClose={onClose} open={open} maxWidth="md" fullWidth>
            <DialogTitle>Create News</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="newText"
                    label="New Text"
                    fullWidth
                    value={text}
                    onChange={handleChange}
                    multiline
                    rows={8} // Initial number of rows
                    required={true}
                    variant="outlined"
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

export default SchoolNewsCreateDialog;