import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import AccountMenu from "./AccountMenu";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";

export default function ButtonAppBar() {
    return (
        <AppBar position="absolute" sx={{
            width: '100%',
            backgroundColor: "#3f51b5" }}
        >
            <Toolbar>
                <Button
                    color="inherit"
                    component={Link}
                    to="/home"
                    sx={{ mr: 2 }}
                >
                    Home
                </Button>
                <Box
                    sx={{
                        flexGrow: 1,
                        display: "flex",
                        justifyContent: "center",
                    }}
                >
                </Box>
                <AccountMenu />
            </Toolbar>
        </AppBar>
    );
}
