import React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SubjectData from "./SubjectData";
import TeacherData from "../teacher/TeacherData";
import {Link} from "react-router-dom";

const SubjectWithTeacherForProfile = ({ subject: { id, name, teacher } }) => {
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

                <Box width="100%" display="flex" alignItems="center">
                    <Typography variant="subtitle1" sx={{ margin: 0, fontWeight: 'bold', color: '#333' }}>
                        Name:
                    </Typography>
                    <Typography variant="subtitle1" sx={{ margin: 0, color: '#333' }}>
                        <SubjectData subject={{ id, name }} />
                    </Typography>
                </Box>

                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="subtitle1" sx={{ margin: 0, fontWeight: 'bold', color: '#333' }}>
                        Teacher:
                    </Typography>
                    <Typography variant="subtitle1" sx={{ margin: 0, color: '#333' }}>
                        {teacher && (
                            <Link to={`/teachers/${teacher.id}`} style={{ color: 'inherit' }}>
                                <TeacherData teacher={teacher} />
                            </Link>
                        )}
                    </Typography>
                </Box>
            </Box>
        </Box>
    );
}

export default SubjectWithTeacherForProfile;
