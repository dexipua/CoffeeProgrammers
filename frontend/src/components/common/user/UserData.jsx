const UserData = ({name, email}) => {
    return (
        <div className="userData">
            <p>Name: {name}</p>
            <p>Email: {email}</p>
        </div>
    )
}

export default UserData;