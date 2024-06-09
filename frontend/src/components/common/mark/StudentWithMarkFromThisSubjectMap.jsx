import StudentWithMarkFromThisSubject from "./StudentWithMarkFromThisSubject";

const StudentWithMarkFromThisSubjectMap = ({ marks }) => {
    return (
        <div>
            {marks.map((mark, index) => (
                <StudentWithMarkFromThisSubject
                    key={index}
                    studentWithMarks={mark}
                />
            ))}
        </div>
    );
};

export default StudentWithMarkFromThisSubjectMap;
