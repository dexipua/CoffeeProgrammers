import React, {useState} from 'react';
import AddIcon from '@mui/icons-material/Add';
import IconButton from "@mui/material/IconButton";
import SchoolNewsCreateDialog from "./SchoolNewsCreateDialog";

function CreateNewsAddButton({onCreate}) {
    const [open, setOpen] = useState(false);

    const handleOpenDialog = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <IconButton
                aria-label="Create news"
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
                <AddIcon sx={{ fontSize: 35 }} />
            </IconButton>
            <SchoolNewsCreateDialog
                open={open}
                onClose={handleClose}
                onCreate={onCreate}
            />
        </>
    );
}

export default CreateNewsAddButton;
