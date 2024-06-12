import Typography from "@mui/material/Typography";

const UserData = ({name, email}) => {
    return (
        <Typography variant="body1" align="center">
            {name}
            <br/>
            {email}
        </Typography>
    )
}

export default UserData;