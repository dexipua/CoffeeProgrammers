import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import InputBase from "@mui/material/InputBase";
import SearchIcon from "@mui/icons-material/Search";
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
                    to="/news"
                    sx={{ mr: 2 }}
                >
                    News
                </Button>
                <Box
                    sx={{
                        flexGrow: 1,
                        display: "flex",
                        justifyContent: "center",
                    }}
                >
                    <InputBase
                        placeholder="Search..."
                        inputProps={{ "aria-label": "search" }}
                        sx={{
                            color: "inherit",
                        }}
                    />
                    <IconButton
                        size="large"
                        edge="end"
                        color="inherit"
                        aria-label="search"
                    >
                        <SearchIcon />
                    </IconButton>
                </Box>
                <AccountMenu />
            </Toolbar>
        </AppBar>
    );
}
