import StudentSimple from "./StudentSimple";

const StudentSimpleMap= ({users}) => {
    return (
        <div>
            {users.map((user) => (
                <div key={user.id} className="card">
                    <div className="card-content">
                        <StudentSimple
                            id={user.id}
                            name={user.lastName + " " + user.firstName}
                        />
                    </div>
                </div>
            ))}
        </div>
    )
}
export default StudentSimpleMap