import React from 'react';
import Box from "@mui/material/Box";
import UserBox from "./UserBox";

const UserList = ({role, teachers}) => {

    return (
        <Box>
            {teachers.map((teacher) => (
                <UserBox
                    key={teacher.id}
                    teacher={teacher}
                    role={role}
                />
            ))}
        </Box>
    );
};

export default UserList;
