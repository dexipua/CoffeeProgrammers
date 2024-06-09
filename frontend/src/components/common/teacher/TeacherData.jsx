const TeacherData = ({teacher: {id, lastName, firstName}}) => {
    return (
        lastName + " " + firstName
    );
};

export default TeacherData;
