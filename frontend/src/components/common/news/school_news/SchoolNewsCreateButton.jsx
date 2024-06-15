import React, {useState} from 'react';
import IconButton from "@mui/material/IconButton";
import SchoolNewsCreateDialog from "./SchoolNewsCreateDialog";
import AddBoxIcon from '@mui/icons-material/AddBox';

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
                color={"default"}
                sx={{
                    color: '#1c1eab',
                    minWidth: 'unset',
                    width: 'auto'
                }}
            >
                <AddBoxIcon sx={{ fontSize: 35 }} />
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
