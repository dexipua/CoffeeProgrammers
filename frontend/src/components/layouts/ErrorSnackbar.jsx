import Snackbar from "@mui/material/Snackbar";
import React from "react";

const ErrorSnackbar = ({message,open, onClose}) => {
    return(
        <Snackbar
            open={open}
            autoHideDuration={3000}
            onClose={onClose}
            message={message}
        />
    )

}

export default ErrorSnackbar;