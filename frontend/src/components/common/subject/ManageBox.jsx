import ChangeSubjectNameButton from "./change_name_button/ChangeSubjectNameButton";
import Box from "@mui/material/Box";
import AddStudentButton from "./AddStudentButton";

const ManageBox = ({subjectId, onNameChange, onStudentAdd }) => (
    <Box
        p={2}
    >
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