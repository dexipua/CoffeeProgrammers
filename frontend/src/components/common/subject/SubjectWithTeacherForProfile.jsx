import React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";

const SubjectWithTeacherForProfile = ({subject}) => {
    return (
        <Box mt="80px" ml="60px">
            <Box
                width={300}
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
                <Typography variant="h6" component="h3">Subject</Typography>

                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="subtitle1" sx={{ margin: 0, fontWeight: 'bold', color: '#333' }}>
                        Name:
                    </Typography>
                    <Typography variant="subtitle1" sx={{ margin: 0, color: '#333' }}>
                        {subject.name}
                    </Typography>
                </Box>

                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="subtitle1" sx={{ margin: 0, fontWeight: 'bold', color: '#333' }}>
                        Teacher:
                    </Typography>
                    <Typography variant="subtitle1" sx={{ margin: 0, color: '#333' }}>
                        {subject.teacher && (
                            <Link to={`/teachers/${subject.teacher.id}`} style={{ color: 'inherit' }}>
                                {subject.teacher.lastName + " " + subject.teacher.firstName}
                            </Link>
                        )}
                    </Typography>
                </Box>
            </Box>
        </Box>
    );
}

export default SubjectWithTeacherForProfile;
