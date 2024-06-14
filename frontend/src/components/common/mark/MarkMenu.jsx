import React, {useState} from 'react';
import {Menu, MenuItem} from '@mui/material';
import ConfirmDialog from "../../layouts/ConfirmDialog";
import MarkService from "../../../services/MarkService";
import EditMarkDialog from "./EditMarkDialog";

function MarkMenu({mark,  onMarkUpdate, onMarkDelete}) {
    const [anchorEl, setAnchorEl] = useState(null);
    const [isHovered, setIsHovered] = useState(false);
    const [openConfirmDialog, setOpenConfirmDialog] = React.useState(false)
    const [openEditDialog, setEditDialog] = React.useState(false)

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleDelete = () => {
        setOpenConfirmDialog(true);
    };

    const handleEdit = () => {
        setEditDialog(true);

    };

    const handleCloseConfirmDialog = () => {
        setOpenConfirmDialog(false);
    };

    const functionDelete = async () => {
        await MarkService.delete(mark.id, localStorage.getItem('jwtToken'));
        onMarkDelete()
        handleMenuClose();
    };

    const handleCloseEditDialog = () => {
        handleMenuClose();
        setEditDialog(false);
    };



    return (
        <>
             <span
                 onClick={handleMenuOpen}
                 style={{cursor: 'pointer', color: isHovered ? 'blue' : 'inherit'}}
                 onMouseEnter={() => setIsHovered(true)}
                 onMouseLeave={() => setIsHovered(false)}
             >
                {mark.value}
            </span>
            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
            >
                <MenuItem onClick={handleEdit}>Edit</MenuItem>

                <EditMarkDialog
                    markId={mark.id}
                    open={openEditDialog}
                    onClose={handleCloseEditDialog}
                    onMarkUpdate={onMarkUpdate}
                />



                <MenuItem onClick={handleDelete}>Delete</MenuItem>

                <ConfirmDialog
                    onClose={handleCloseConfirmDialog}
                    open={openConfirmDialog}
                    text={"Do you really want to delete this mark?"}
                    someFunction={() => {
                          functionDelete()
                    }}
                />

            </Menu>
        </>
    );
}

export default MarkMenu;
