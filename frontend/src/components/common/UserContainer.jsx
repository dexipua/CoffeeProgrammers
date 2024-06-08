import UserData from "./UserData";

function UserContainer(user){
    return(
        <div>
            <p>Account</p>
            <UserData
                name={user.name}
                email={user.email}
            />
        </div>
    )
}