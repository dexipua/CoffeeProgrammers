import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import React from "react";
import StudentTable from "../student/StudentTable";

const TeacherStudentsBox = ({students}) => {
    return (
        <Box
            sx={{
                width: '100%',
                height:'auto',
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
                textAlign: 'center',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                mt: 2
            }}
        >
            <Typography mt="10px" variant="h6" component="h3">Students</Typography>
            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    justifyContent: 'center',
                }}
            >
                <StudentTable
                    students={students}
                />
            </Box>
        </Box>

    )
}

export default TeacherStudentsBox;