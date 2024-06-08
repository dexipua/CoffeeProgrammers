import TeacherData from "./TeacherData";

const TeacherList = ({teachers}) => {
    return (
        <div className="cards">
            {teachers.map((teacher) => (
                <div key={teacher.id} className="card">
                    <div className="card-content">
                        <TeacherData
                            name={teacher.lastName + " " + teacher.firstName}
                        />
                    </div>
                </div>
            ))}
        </div>
    )
}
export default TeacherList