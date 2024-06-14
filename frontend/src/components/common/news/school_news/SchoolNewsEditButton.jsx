import * as React from 'react';
import EditIcon from '@mui/icons-material/Edit';
import SchoolNewsEditDialog from "./SchoolNewsEditDialog";
import IconButton from "@mui/material/IconButton";


export default function SchoolNewsEditButton({newsId, oldText, updateFunction}) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <IconButton
                onClick={handleClickOpen}
                onMouseEnter={(e) => e.currentTarget.style.color = 'green'}
                onMouseLeave={(e) => e.currentTarget.style.color = ''}
                aria-label="delete"
                sx={{ minWidth: 'unset', width: 'auto', p: 0, fontSize: 24  }}
            >
                <EditIcon/>
            </IconButton>
            <SchoolNewsEditDialog
                newsId={newsId}
                oldText={oldText}
                open={open}
                updateFunction={updateFunction}
                onClose={handleClose}
            />
        </>
    );
}

