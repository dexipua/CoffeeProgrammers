import SubjectWithTeacher from "./SubjectWithTeacher";

const SubjectWithTeacherList = ({ subjects }) => {
    return (

            subjects.map((subject, index) => (
                <SubjectWithTeacher
                    key={index}
                    subjectResponse={subject}
                />
            ))

    );
};

export default SubjectWithTeacherList;
