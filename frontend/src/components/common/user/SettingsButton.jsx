import SettingsIcon from "@mui/icons-material/Settings";
import React, {useState} from "react";
import ChangeNameDialog from "./ChangeNameDialog";

const SettingsButton = ({user, onNameUpdate}) => {
    const [open, setOpen] = useState(false)

    return (
        <>
            <SettingsIcon
                onClick={() => setOpen(true)}
                fontSize="small"
                style={{color: 'black', cursor: 'pointer'}}
                onMouseEnter={(e) => e.currentTarget.style.color = 'grey'}
                onMouseLeave={(e) => e.currentTarget.style.color = 'black'}
            />
            <ChangeNameDialog
                user={user}
                onClose={() => setOpen(false)}
                open={open}
                onNameUpdate={onNameUpdate}
            />
        </>
    )
}

export default SettingsButton;