import React from "react";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

const AverageMarkView = ({averageMark}) => {
    let textColor;

    if (averageMark >= 10) {
        textColor = "green";
    } else if (averageMark >= 6) {
        textColor = "orange";
    } else {
        textColor = "red";
    }

    return (
        <Box
            width="300px"
            height="auto"
            my={2}
            display="flex"
            flexDirection="column"
            justifyContent="flex-start"
            alignItems="flex-start"
            gap={1}
            p={2}
            sx={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
            }}
        >
            <Typography variant="subtitle1"
                        sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                Average mark:
            </Typography>
            <Box display="flex" alignItems="center">
                <Typography variant="h6" component="span" style={{color: textColor}}>
                    {averageMark}
                </Typography>
                <Typography variant="h6" component="span"  style={{color: '#3C3C3C' }}>
                    /12
                </Typography>
            </Box>
        </Box>
    );
};

export default AverageMarkView;
