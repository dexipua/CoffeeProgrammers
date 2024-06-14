import React from 'react';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';

const SubjectSearchBar = ({name, onNameChange, onSearch}) => {
    const handleSearchClick = async () => {
        await onSearch();
    };

    return (
        <div style={{display: 'flex', alignItems: 'center', gap: '8px'}}>
            <TextField
                type="text"
                value={name}
                onChange={onNameChange}
                placeholder="Subject"
                required

            />
            <IconButton onClick={handleSearchClick} aria-label="search">
                <SearchIcon/>
            </IconButton>
        </div>

    );
};

export default SubjectSearchBar;