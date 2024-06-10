import UserData from "./UserData";

function AccountContainer({user}){
    return(
        <div>
            <UserData
                name={user.lastName + " " + user.firstName}
                email={user.email}
            />
        </div>
    )
}
export default AccountContainer