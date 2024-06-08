import UserData from "./UserData";

function AccountContainer({user}){
    return(
        <div>
            <p>Account</p>
            <UserData
                name={user.lastName + " " + user.firstName}
                email={user.email}
            />
        </div>
    )
}
export default AccountContainer