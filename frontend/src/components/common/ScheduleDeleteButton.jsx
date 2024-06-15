import React, {useState} from 'react';
import {Button} from '@mui/material';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import EventBusyIcon from '@mui/icons-material/EventBusy';
import ConfirmDialog from "../layouts/ConfirmDialog";

const ScheduleDeleteButton = ({onDelete}) => {
    const [hovered, setHovered] = useState(false);
    const [openDialog, setOpenDialog] = useState(false)

    return (
        <>
            <Button
                onClick={() => setOpenDialog(true)}
                sx={{
                    color: "#5B5B5BEF",
                    width: '40px',
                    height: '50px',
                }}
                onMouseEnter={() => setHovered(true)}
                onMouseLeave={() => setHovered(false)}
            >
                {hovered ? <EventBusyIcon/> : <EventAvailableIcon/>}
            </Button>
            <ConfirmDialog
                open={openDialog}
                onClose={() => {
                    setOpenDialog(false)
                }}
                text={"Do you really want to delete this date?"}
                someFunction={onDelete}
            />
        </>
    );
};

export default ScheduleDeleteButton;
