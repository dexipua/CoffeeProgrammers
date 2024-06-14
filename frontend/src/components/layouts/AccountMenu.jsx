import * as React from 'react';
import IconButton from '@mui/material/IconButton';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import {Link} from "react-router-dom";

export default function AccountMenu() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const role = localStorage.getItem('role');
    const roleId = localStorage.getItem('roleId')

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    return (
        <div>
            <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-account"
                aria-haspopup="true"
                onClick={handleMenuOpen}
                color="inherit"
            >
                <AccountCircleIcon fontSize={"large"}/>
            </IconButton>
            <Menu
                id="menu-account"
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
            >
                <MenuItem onClick={handleMenuClose}>
                    <Link to= {role === "STUDENT" ? `/students/${roleId}` : `/teachers/${roleId}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                        Account
                    </Link>
                </MenuItem>
                <MenuItem onClick={handleMenuClose}>
                    <Link to="/subjects" style={{ textDecoration: 'none', color: 'inherit' }}>
                        Subjects
                    </Link>
                </MenuItem>
                <MenuItem onClick={handleMenuClose}>
                    <Link to="/users" style={{ textDecoration: 'none', color: 'inherit' }}>
                        Users
                    </Link>
                </MenuItem>
                <MenuItem onClick={handleMenuClose}>
                    <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
                        Logout
                    </Link>
                </MenuItem>
            </Menu>
        </div>
    );
}
