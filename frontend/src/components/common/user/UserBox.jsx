import Box from "@mui/material/Box";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";
import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";
import React from "react";

const UserBox = ({role, user}) => {
    return (
        <Link to={`/${ role === "STUDENT" ? "teachers" : "students"}/${user.id}`}
              style={{textDecoration: 'none', color: 'inherit'}}>
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
                <Box display="flex" alignItems="center">
                    <AssignmentIndIcon
                        fontSize="large"
                        style={{
                            color: '#333',
                            marginRight: 8
                        }}/>
                    <Box>
                        <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                            <Typography variant="subtitle1" sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                                {role === "STUDENT" ? "Teacher:" : "Student:"}
                            </Typography>
                            <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                                {user.firstName + " " + user.lastName}
                            </Typography>
                        </Box>
                        <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                            <Typography variant="subtitle1" sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                                Email:
                            </Typography>
                            <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                                {user.email}
                            </Typography>
                        </Box>
                    </Box>
                </Box>
            </Box>
        </Link>
    );
}

export default UserBox;