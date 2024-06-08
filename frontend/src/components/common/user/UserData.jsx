const UserData = (name, email, role) => {
    return (
        <div className="userData">
            <p>Name: ${name}</p>
            <p>Email: ${email}</p>
            <p>Role: ${role}</p>
        </div>
    )
}

export default UserData;