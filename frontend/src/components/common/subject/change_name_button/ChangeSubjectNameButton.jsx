// ChangeSubjectNameButton.js
import React, {useState} from 'react';
import Button from "@mui/material/Button";
import ChangeSubjectNameDialog from "./ChangeSubjectNameDialog";

const ChangeSubjectNameButton = ({ subjectId, onNameChange }) => {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button
                variant="contained"
                onClick={handleClickOpen}
            >
                Change name
            </Button>
            <ChangeSubjectNameDialog
                subjectId={subjectId}
                onClose={handleClose}
                open={open}
                onNameChange={onNameChange}
            />
        </div>
    );
}

export default ChangeSubjectNameButton;
