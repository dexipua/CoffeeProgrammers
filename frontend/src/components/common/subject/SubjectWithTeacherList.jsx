import SubjectWithTeacher from "./SubjectWithTeacher";

const SubjectWithTeacherList = ({subjects}) => {
    console.log(subjects);
    return (
            <div className="cards">
                {subjects.map((subject) => (
                    <div key={subject.id} className="card">
                        <div className="card-content">
                            <SubjectWithTeacher
                                subject={subject}
                                teacher={subject.teacher}/>
                        </div>
                    </div>
                ))}
            </div>
    )
}
export default SubjectWithTeacherList