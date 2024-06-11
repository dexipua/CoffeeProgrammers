import React, {useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import MarkService from "../../../services/MarkService";

function EditMarkDialog({markId, open, onClose, onMarkUpdate}) {
    const [newMark, setNewMark] = useState('');

    const handleAccept = async () => {
        const updatedMark = await MarkService.update(
            markId,
            {value: newMark},
            localStorage.getItem('jwtToken'))
        onMarkUpdate(updatedMark)
        onClose();
    };

    const handleChange = (event) => {
        setNewMark(event.target.value);
    };

    return (
        <Dialog onClose={onClose} open={open} maxWidth="xs" fullWidth>
            <DialogTitle>Edit Mark</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="newMark"
                    label="New Mark"
                    fullWidth
                    value={newMark}
                    onChange={handleChange}
                />
                <div style={{ display: 'flex', justifyContent: 'center', marginTop: '16px' }}>
                    <Button variant="contained" onClick={onClose} style={{ marginRight: '8px' }} color="error">
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

export default EditMarkDialog;