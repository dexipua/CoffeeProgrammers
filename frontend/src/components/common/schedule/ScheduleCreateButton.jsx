import React, {useState} from 'react';
import {Button} from '@mui/material';
import ConfirmDialog from "../../layouts/ConfirmDialog";
import EditCalendarIcon from "@mui/icons-material/EditCalendar";

const ScheduleCreateButton = ({onCreate}) => {
    const [openDialog, setOpenDialog] = useState(false)

    return (
        <>
            <Button
                onClick={() => setOpenDialog(true)}
                sx={{
                    color: "#5B5B5BEF",
                    width: '40px',
                    height: '50px',
                    opacity: 0,
                    transition: 'opacity 0.3s ease',
                    ':hover': {
                        opacity: 1,
                    },
                }}
            >
                <EditCalendarIcon/>
            </Button>
            <ConfirmDialog
                open={openDialog}
                onClose={() => {
                    setOpenDialog(false)
                }}
                text={"You really want to create a subject with this date?"}
                someFunction={onCreate}
            />
        </>
    );
};

export default ScheduleCreateButton;
