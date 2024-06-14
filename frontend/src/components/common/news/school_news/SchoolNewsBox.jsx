import React from 'react';
import {Box, Typography} from '@mui/material';
import DeleteButton from "../../../layouts/DeleteButton";
import SchoolNewsEditButton from "./SchoolNewsEditButton";


const SchoolNewsBox = ({text, time, updateFunction, deleteFunction }) => {
    return (
        <Box
            sx={{
                border: '1px solid #ccc',
                borderRadius: '8px',
                padding: '16px',
                marginBottom: '6px',
                marginTop: '6px',
                backgroundColor: '#ffffff'
            }}
        >

            <Typography variant="body1" component="h3" gutterBottom>
                {text}
            </Typography>

            <Typography variant="body2" color="textSecondary">
                {new Date(time).toLocaleString()}
            </Typography>
            <DeleteButton
                text={"Do you really want to delete this news?"}
                deleteFunction={deleteFunction}
            />
            <SchoolNewsEditButton
                updateFunction={updateFunction}
            />
        </Box>
    );
};

export default SchoolNewsBox;
