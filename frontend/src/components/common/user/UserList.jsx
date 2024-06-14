import React from 'react';
import Box from "@mui/material/Box";
import UserBox from "./UserBox";

const UserList = ({role, teachers}) => {

    return (
        <Box>
            {teachers.map((user) => (
                <UserBox
                    key={user.id}
                    user={user}
                    role={role}
                />
            ))}
        </Box>
    );
};

export default UserList;
