import * as React from 'react';
import PropTypes from 'prop-types';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import AddStudentList from "../student/AddStudentList";
import Box from "@mui/material/Box";

function AddStudentDialog({ onClose, open, subjectId}) {
    const handleClose = () => {
        onClose();
    };

    return (
        <Dialog onClose={handleClose} open={open} maxWidth="md" fullWidth>
            <DialogTitle>Add Student</DialogTitle>
            <DialogContent style={{ position: 'relative' }}>
                <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    height="100%"
                >
                    <AddStudentList subjectId={subjectId} />
                </Box>
                <Button
                    onClick={handleClose}
                    variant="contained"
                    style={{
                        backgroundColor: 'red',
                        position: 'sticky',
                        bottom: '16px',
                        right: '16px',
                    }}
                >
                    Close
                </Button>
            </DialogContent>
        </Dialog>
    );
}

AddStudentDialog.propTypes = {
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    subjectId: PropTypes.number.isRequired,
};

export default function AddStudentButton({ subjectId }) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button variant="contained" onClick={handleClickOpen}>
                Add Student
            </Button>
            <AddStudentDialog
                onClose={handleClose}
                open={open}
                subjectId={subjectId}
            />
        </div>
    );
}

