import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";

export default function ButtonAppBar() {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <Box
                        sx={{
                            flexGrow: 1,
                            display: "flex",
                            justifyContent: "space-between",
                        }}
                    >
                        <Button color="inherit">News</Button>
                        <Button color="inherit">Search</Button>
                        <Button color="inherit">Account</Button>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
