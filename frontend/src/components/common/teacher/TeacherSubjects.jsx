import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";
import Box from "@mui/material/Box";
import React from "react";

const TeacherSubjects = ({subjects}) => {
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
            <Typography variant="h6" component="h3" gutterBottom>
                Subjects
            </Typography>

            {subjects.length > 0 ? (
                subjects.map((subject) => (
                    <Link to={`http://localhost:3000/subjects/${subject.id}`} style={{textDecoration: 'none', color: 'inherit'}}>
                        <Box
                            width="265px"
                            height="auto"
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
                            <div style={{display: 'flex', alignItems: 'center'}}>
                                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                                    <Typography variant="subtitle1" gap={1}
                                                sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                                        Subject:
                                    </Typography>
                                    <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                                        {subject.name} <br/>
                                    </Typography>
                                </Box>
                            </div>
                        </Box>
                    </Link>
                ))
            ) : (
                <Typography margin="10px" variant="body1" component="h3">
                    No subjects available
                </Typography>
            )}

        </Box>
    )
}

export default TeacherSubjects;