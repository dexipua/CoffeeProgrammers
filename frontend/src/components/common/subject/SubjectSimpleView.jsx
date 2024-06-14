import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import React from "react";
import {Link} from "react-router-dom";

const SubjectSimpleView = ({subject}) => {
    return (
        <Link to={`/subjects/${subject.id}`} style={{textDecoration: 'none', color: 'inherit'}}>
            <Box
                width="600px"
                height="auto"
                my={2}
                mr={2}
                ml={2}
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
                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="subtitle1" sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                        Subject:
                    </Typography>
                    <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                        {subject.name}
                    </Typography>
                </Box>

                <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="subtitle1" sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                        Teacher:
                    </Typography>
                    <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                        {subject.teacher && (
                            subject.teacher.firstName + " " + subject.teacher.lastName
                        )}
                    </Typography>
                </Box>
            </Box>
        </Link>
    );
}

export default SubjectSimpleView;