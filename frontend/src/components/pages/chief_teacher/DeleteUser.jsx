import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import TeacherService from '../../../services/TeacherService';

const DeleteUser = () => {
    const [role, setRole] = useState('student');
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchUsers = async () => {
            setIsLoading(true);
            try {
                let response;
                if (role === 'student') {
                    response = await StudentService.getAll(token);
                } else {
                    response = await TeacherService.getAll(token);
                }
                setUsers(response);
            } catch (error) {
                console.error('Error fetching users:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchUsers();
    }, [role, token]); // Include role and token in dependencies

    const handleDelete = async () => {
        if (!selectedUser) return;
        try {
            if (role === 'student') {
                await StudentService.delete(selectedUser.id, token);
            } else {
                await TeacherService.delete(selectedUser.id, token);
            }
            setUsers(users.filter(user => user.id !== selectedUser.id));
            setSelectedUser('');
        } catch (error) {
            console.error('Error deleting user:', error);
        }
    };

    return (
        <div className="delete-container">
            <h2>Delete User</h2>
            <div className="role-select">
                <label>Role:</label>
                <select value={role} onChange={(e) => setRole(e.target.value)}>
                    <option value="student">Student</option>
                    <option value="teacher">Teacher</option>
                </select>
            </div>
            <div className="user-select">
                <label>Select User:</label>
                <form>
                    {isLoading ? (
                        <div>Loading...</div>
                    ) : (
                        users.length > 0 && users.map(user => (
                            <div key={user.id}>
                                <input
                                    type="radio"
                                    id={user.id}
                                    name="selectedUser"
                                    value={JSON.stringify(user)}
                                    checked={selectedUser === JSON.stringify(user)}
                                    onChange={(e) => setSelectedUser(JSON.parse(e.target.value))}
                                />
                                <label htmlFor={user.id}>{user.firstName} {user.lastName}</label>
                            </div>
                        ))
                    )}
                </form>
            </div>
            <button onClick={handleDelete} disabled={!selectedUser}>Delete</button>
        </div>
    );
};

export default DeleteUser;
