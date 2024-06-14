import Typography from "@mui/material/Typography";
import ChangeSubjectNameButton from "./change_name_button/ChangeSubjectNameButton";
import Box from "@mui/material/Box";
import AddStudentButton from "./AddStudentButton";

const ManageBox = ({subjectId, onNameChange, onStudentAdd }) => (
    <Box
        width={300}
        mt={4}
        ml="60px"
        p={2}
        sx={{
            border: '1px solid #ddd',
            borderRadius: '8px',
            backgroundColor: '#ffffff'
        }}
    >
        <Typography mb="5px" variant="h6" component="h3">
            Manage
        </Typography>
        <Box mb="10px">
            <ChangeSubjectNameButton
                subjectId={subjectId}
                onNameChange={onNameChange}
            />
        </Box>
        <Box>
            <AddStudentButton
                onStudentAdd={onStudentAdd}
                subjectId={subjectId} />
        </Box>
    </Box>
);

export default ManageBox;