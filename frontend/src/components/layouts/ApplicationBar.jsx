import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import AccountMenu from "./AccountMenu";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";

export default function ApplicationBar() {
    const role = localStorage.getItem('role');
    return (
        <AppBar  position="absolute" sx={{
            width: '100%',
            backgroundColor: "#3f51b5" }}
        >
            <Toolbar>
                <Box>
                    <Button
                        color="inherit"
                        component={Link}
                        to="/home"
                        sx={{ mr: 2 }}
                        style={{ textDecoration: 'none', color: 'inherit' }}
                    >
                        Home
                    </Button>
                    <Button
                        color="inherit"
                        component={Link}
                        to="/news"
                        sx={{ mr: 2 }}
                        style={{ textDecoration: 'none', color: 'inherit' }}
                    >
                        news
                    </Button>
                    {role === "STUDENT" ? (
                        <Button
                            color="inherit"
                            component={Link}
                            to="/bookmark"
                            sx={{ mr: 2 }}
                            style={{ textDecoration: 'none', color: 'inherit' }}
                        >
                            Bookmark
                        </Button>
                    ) : (role === "CHIEF_TEACHER" && (
                        <Button
                            color="inherit"
                            component={Link}
                            to="/admin_panel"
                            sx={{ mr: 2 }}
                            style={{ textDecoration: 'none', color: 'inherit' }}
                        >
                            Admin panel
                        </Button>
                    ))}
                </Box>

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
