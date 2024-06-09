const StudentWithMarkFromThisSubject = ({studentWithMarks: {
    studentResponseSimple: {
        studentId,
        firstName,
        lastName
    },
    marks
}}) => {
    return (
        <div key={studentId}>
            <p>{firstName + " " + lastName}</p>
            <p>{marks.map((mark, index) => (
                <span key={index}>{mark.value}, </span>
            ))}</p>
        </div>
    )
}
export default StudentWithMarkFromThisSubject
