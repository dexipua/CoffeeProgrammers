import React from 'react';
import TeacherData from "./TeacherData";
import Box from "@mui/material/Box";
import {Link} from "react-router-dom";
import Typography from "@mui/material/Typography";

const TeacherList = ({teachers}) => {
    const containerStyle = {
        display: 'flex',
        flexWrap: 'wrap', // Allows items to wrap to the next line if necessary
        justifyContent: 'center', // Centers the items horizontally
        gap: '16px', // Adds spacing between items
        padding: '16px', // Adds padding to the container
    };

    return (
        <div style={containerStyle}>
            {teachers.map((teacher) => (
                <Link key={teacher.id} to={`/teachers/${teacher.id}`}
                      style={{textDecoration: 'none', color: 'inherit'}}>
                    <Box
                        width={350}
                        height={60}
                        display="flex"
                        flexDirection="column"
                        justifyContent="center"
                        alignItems="center"
                        p={2}
                        sx={{
                            border: '1px solid #ccc',
                            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                            borderRadius: '8px',
                            backgroundColor: '#FFFFFF',
                            transition: 'transform 0.2s, box-shadow 0.2s',
                            '&:hover': {
                                transform: 'scale(1.02)',
                                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                            },
                        }}
                    >
                        <Typography variant="h6" align="center">
                            <TeacherData teacher={teacher}/>
                        </Typography>
                    </Box>
                </Link>
            ))}
        </div>
    );
};

export default TeacherList;
