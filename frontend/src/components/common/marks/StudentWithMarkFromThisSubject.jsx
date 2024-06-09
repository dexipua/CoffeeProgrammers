const StudentWithMarkFromThisSubject = ({studentWithMarks: {
    studentResponseSimple: {
        studentId,
        firstName,
        lastName
    },
    marks
}}) => {
    return (
        <div>
            <p>{firstName + " " + lastName}</p>
            <p>{marks.map((mark) => (
                mark.value + ", "
            ))}</p>
        </div>
    )
}
export default StudentWithMarkFromThisSubject