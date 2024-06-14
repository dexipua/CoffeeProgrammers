import React, {useEffect, useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import StudentService from "../../../services/StudentService";
import TeacherService from "../../../services/TeacherService";

const ChangeNameDialog = ({user, open, onClose, onNameUpdate}) => {
    const [newFirstName, setNewFirstName] = useState(user.firstName);
    const [newLastName, setNewLastName] = useState(user.lastName);
    const [newPassword, setNewPassword] = useState('');
    const [errorMessages, setErrorMessages] = useState([]);

    useEffect(() => {
        setErrorMessages([]);
    }, [open]);

    const handleAccept = async () => {
        const role = localStorage.getItem('role');
        const jwtToken = localStorage.getItem('jwtToken');
        try {
            role === "STUDENT" ? (
                await StudentService.update(
                    user.id,
                    {
                        firstName: newFirstName,
                        lastName: newLastName,
                        password: newPassword
                    },
                    jwtToken
                )
            ) : (
                await TeacherService.update(
                    user.id,
                    {
                        firstName: newFirstName,
                        lastName: newLastName,
                        password: newPassword
                    },
                    jwtToken
                )
            )

            onNameUpdate(newFirstName, newLastName)
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

    const handleFirstName = (event) => {
        setNewFirstName(event.target.value);
    };

    const handleLastName = (event) => {
        setNewLastName(event.target.value);
    };

    const handlePassword = (event) => {
        setNewPassword(event.target.value);
    };

    return (
        <Dialog onClose={onClose} open={open} maxWidth="xs" fullWidth>
            <DialogTitle>Edit menu</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="firstName"
                    label="First name"
                    fullWidth
                    value={newFirstName}
                    onChange={handleFirstName}
                />
                <TextField
                    autoFocus
                    margin="dense"
                    id="lastName"
                    label="Last name"
                    fullWidth
                    value={newLastName}
                    onChange={handleLastName}
                />
                <TextField
                    autoFocus
                    margin="dense"
                    id="password"
                    label="Password"
                    fullWidth
                    value={newPassword}
                    onChange={handlePassword}
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

export default ChangeNameDialog;