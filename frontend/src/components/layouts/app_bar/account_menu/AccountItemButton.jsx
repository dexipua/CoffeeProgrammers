import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import * as React from "react";

const AccountItemButton = ({text, link}) => {
    return (
        <Button
            variant="contained"
            component={Link}
            to={link}
            size="small"
            style={{
                background: "#d5d5d5",
                border: '1px solid #ddd',
                borderRadius: '6x',
                textDecoration: 'none',
                color: "inherit"
            }}>
            {text}
        </Button>
    )
}

export default AccountItemButton;