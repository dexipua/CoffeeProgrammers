import SubjectData from "./SubjectData";
import TeacherData from "../teacher/TeacherData";


const SubjectWithTeacher = ({subject, teacher}) => {
    return(
        <div>
            <SubjectData
                name={subject.name}
            />
            <TeacherData
                name={teacher.lastName + " " + teacher.firstName}
                />
        </div>
    )
}
export default SubjectWithTeacher