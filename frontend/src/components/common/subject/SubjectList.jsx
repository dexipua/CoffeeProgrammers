import SubjectData from "./SubjectData";

const SubjectList = ({subjects}) => {
    return (
            <div className="cards">
                {subjects.map((subject) => (
                    <div key={subject.id} className="card">
                        <div className="card-content">
                            <SubjectData
                                name={subject.name}/>
                        </div>
                    </div>
                ))}
        </div>
    )
}

export default SubjectList