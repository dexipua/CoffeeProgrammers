import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import * as React from "react";

const AccountItemButton = ({text, link}) => {
    return (
        <Button
            color="inherit"
            component={Link}
            to={link}
            sx={{ mr: 2 }}
            style={{ textDecoration: 'none', color: 'inherit' }}
        >
            {text}
        </Button>
    )
}

export default AccountItemButton;