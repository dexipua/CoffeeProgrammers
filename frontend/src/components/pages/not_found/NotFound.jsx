import React from 'react';
import {Box, Typography} from '@mui/material';
import {Link} from 'react-router-dom';

const NotFound = () => {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                textAlign: 'center'
            }}
        >
            <Typography variant="h2" component="h1" gutterBottom>
                404
            </Typography>
            <Typography variant="h5" component="h2" gutterBottom>
                Page Not Found
            </Typography>
            <Link to="/home" style={{ textDecoration: 'none', color: '#1976d2' }}>
                <Typography variant="body1" component="h2" gutterBottom>
                    Go to home
                </Typography>
            </Link>
        </Box>
    );
};

export default NotFound;
