import StudentSimple from "./StudentSimple";

const StudentSimpleMap = ({users}) => {
    const containerStyle = {
        display: 'flex',
        flexWrap: 'wrap', // Allows items to wrap to the next line if necessary
        justifyContent: 'center', // Centers items horizontally
    };

    const itemStyle = {
        margin: '10px', // Adds spacing between items
    };
    return (
        <div style={containerStyle}>
            {users.map((user) => (
                <div key={user.id} style={itemStyle}>
                    <StudentSimple
                        id={user.id}
                        name={user.lastName + " " + user.firstName}/>
                </div>
            ))}
        </div>
    )
}
export default StudentSimpleMap