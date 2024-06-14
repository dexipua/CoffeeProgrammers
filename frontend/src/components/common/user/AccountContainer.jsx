import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import React from "react";
import SettingsButton from "./SettingsButton";

function AccountContainer({edit, user, topic, onNameUpdate}) {
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
            <Box display="flex" alignItems="center" gap={0.5}>
                {edit && (
                    <SettingsButton
                        user={user}
                        onNameUpdate={onNameUpdate}
                    />
                )}
                <Typography variant="h6" component="h3">{topic}</Typography>
            </Box>
            <Box display="flex" alignItems="center">
                <Box>
                    <Box width="100%" display="flex" alignItems="center" gap={0.5}>
                        <Typography variant="subtitle1" sx={{margin: 0, fontWeight: 'bold', color: '#333'}}>
                            Name:
                        </Typography>
                        <Typography variant="subtitle1" sx={{margin: 0, color: '#333'}}>
                            {user.lastName + " " + user.firstName}
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
    )
}

export default AccountContainer