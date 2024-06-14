import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import AccountMenu from "./account_menu/AccountMenu";
import AccountItemButton from "./AppBarButton";

export default function ApplicationBar() {
    const role = localStorage.getItem('role');
    return (
        <AppBar position="absolute" sx={{
            width: '100%',
            backgroundColor: "#3f51b5"
        }}
        >
            <Toolbar>
                <Box>
                    <AccountItemButton
                        link={"/home"}
                        text={"Home"}

                    />
                    <AccountItemButton
                        link={"/school_news"}
                        text={"School news"}
                    />
                    <AccountItemButton
                        link={"/my_news"}
                        text={"My news"}
                    />
                    <AccountItemButton
                        link={"/subjects"}
                        text={"Subjects"}
                    />
                    <AccountItemButton
                        link={"/users"}
                        text={"Users"}
                    />


                    {role === "STUDENT" ? (
                        <AccountItemButton
                            link={"/bookmark"}
                            text={"Bookmark"}
                        />
                    ) : (role === "CHIEF_TEACHER" && (
                        <AccountItemButton
                            link={"/admin_panel"}
                            text={"Admin panel"}
                        />
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
                <AccountMenu/>
            </Toolbar>
        </AppBar>
    )
        ;
}
