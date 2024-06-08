import UserData from "./UserData";


const UserMap= ({users}) => {
    return (
        <div>
            {users.map((user) => (
                <div key={user.id} className="card">
                    <div className="card-content">
                        <UserData
                            name={user.lastName + " " + user.firstName}
                            email={user.email}/>
                    </div>
                </div>
            ))}
        </div>
    )
}
export default UserMap