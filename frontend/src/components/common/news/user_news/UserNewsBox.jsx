import React from 'react';
import {Box, Typography} from '@mui/material';

const UserNewsBox = ({ text, time}) => {
    return (
        <Box
            sx={{
                border: '1px solid #ccc',
                borderRadius: '8px',
                padding: '16px',
                marginBottom: '6px',
                marginTop: '6px',
                backgroundColor: '#f5f5f5'
            }}
        >

            <Typography variant="body1" component="h3" gutterBottom>
                {text}
            </Typography>
            <Typography variant="body2" color="textSecondary">
                {new Date(time).toLocaleString()}
            </Typography>
        </Box>
    );
};

export default UserNewsBox;
