import React from 'react';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';

const UserSearchBar = ({firstName, lastName, onFirstNameChange, onLastNameChange, onSearch}) => {
    const handleSearchClick = async () => {
        await onSearch();
    };

    return (
        <div style={{display: 'flex', alignItems: 'center', gap: '8px'}}>
            <TextField
                type="text"
                value={firstName}
                onChange={onFirstNameChange}
                placeholder="First Name"
                required
                
            />
            <TextField
                type="text"
                value={lastName}
                onChange={onLastNameChange}
                placeholder="Last Name"
                required
            />
            <IconButton onClick={handleSearchClick} aria-label="search">
                <SearchIcon/>
            </IconButton>
        </div>

    );
};

export default UserSearchBar;