import React, {useState} from 'react';
import AddIcon from '@mui/icons-material/Add';
import CreateMarkDialog from './CreateMarkDialog';
import IconButton from "@mui/material/IconButton";

function CreateMarkButton({ studentId, subjectId, onMarkCreate }) {
    const [dialogOpen, setDialogOpen] = useState(false);

    const handleOpenDialog = () => {
        setDialogOpen(true);
    };

    const handleCloseDialog = (studentId, createdMark) => {
        setDialogOpen(false);
        onMarkCreate(studentId, createdMark);
    };

    return (
        <>
            <IconButton
                aria-label="Add Mark"
                onClick={handleOpenDialog}
                sx={{
                    color: 'green',
                    minWidth: 'unset',
                    width: 'auto',
                    p: 0,
                    '&:hover': {
                        color: ''
                    }
                }}
            >
                <AddIcon sx={{ fontSize: 24 }} />
            </IconButton>
            <CreateMarkDialog
                subjectId={subjectId}
                studentId={studentId}
                open={dialogOpen}
                onClose={handleCloseDialog}
            />
        </>
    );
}

export default CreateMarkButton;
