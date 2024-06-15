import Box from "@mui/material/Box";
import StudentTable from "../student/StudentTable";
import MarkTableForSubject from "../mark/table/MarkTableForSubject";

const StudentsBox = (
    {
        isTeacherOfThisSubject,
        subjectId,
        studentsWithGradesOrEmpty,
        subjectStudents,
        onStudentDelete,
        onMarkCreate,
        onMarkUpdate,
        onMarkDelete
    }) => (
    <Box
        display="flex" flexDirection="column" alignItems="center"
        p={2}
        sx={{
            width: "1200px",
            border: '1px solid #ddd',
            borderRadius: '8px',
            backgroundColor: '#ffffff'
        }}
    >
        {!isTeacherOfThisSubject ? (
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