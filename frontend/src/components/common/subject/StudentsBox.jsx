import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import StudentTable from "../student/StudentTable";
import MarkTableForSubject from "../mark/MarkTableForSubject";

const StudentsBox = (
    {
        canGetMark,
        subjectId,
        studentsWithGradesOrEmpty,
        subjectStudents,
        onStudentDelete,
        onMarkCreate,
        onMarkUpdate,
        onMarkDelete
    }) => (
    <Box
        mt={4}
        ml="60px"
        mr="60px"
        p={2}
        sx={{
            border: '1px solid #ddd',
            borderRadius: '8px',
            backgroundColor: '#ffffff'
        }}
    >
        <Box mb={2}>
            <Typography variant="h6" component="h3">Students</Typography>
        </Box>
        {!canGetMark ? (
            <StudentTable students={subjectStudents} />
        ) : (
            <MarkTableForSubject
                subjectId={subjectId}
                students={studentsWithGradesOrEmpty}
                onStudentDelete={onStudentDelete}
                onMarkCreate={onMarkCreate}
                onMarkUpdate={onMarkUpdate}
                onMarkDelete={onMarkDelete}
            />
        )}
    </Box>
);

export default StudentsBox;