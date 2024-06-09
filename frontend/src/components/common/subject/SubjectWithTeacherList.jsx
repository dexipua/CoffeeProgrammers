import SubjectWithTeacherForList from "./SubjectWithTeacherForList";

const SubjectWithTeacherList = ({subjects}) => {
    return (
        subjects.map((subject, index) => (
            <SubjectWithTeacherForList
                key={index}
                subjectResponse={subject}
            />
        ))

    );
};

export default SubjectWithTeacherList;
