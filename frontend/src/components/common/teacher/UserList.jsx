import React from 'react';
import Box from "@mui/material/Box";
import TeacherBox from "./TeacherBox";

const UserList = ({role, teachers}) => {

    return (
        <Box>
            {teachers.map((teacher) => (
                <TeacherBox
                    key={teacher.id}
                    teacher={teacher}
                    role={role}
                />
            ))}
        </Box>
    );
};

export default UserList;
