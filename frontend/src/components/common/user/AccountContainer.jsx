import UserData from "./UserData";

function AccountContainer(user){
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