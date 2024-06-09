import StudentWithMarkFromThisSubject from "./StudentWithMarkFromThisSubject";

const StudentWithMarkFromThisSubjectMap = ({marks}) => {
    return (
        <div>
            {marks.map((mark) => (
            <StudentWithMarkFromThisSubject
                studentWithMarks={mark}
            />
            ))}
        </div>
    )
}
export default StudentWithMarkFromThisSubjectMap