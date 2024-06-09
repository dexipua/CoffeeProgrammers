import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import StudentService from '../../../services/StudentService';
import TeacherService from '../../../services/TeacherService';
import '../../../assets/styles/CreateUser.css';

const CreateUser = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('student'); // Початкова роль - студент
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState([]);
    const navigate = useNavigate();

    const handleCreateUser = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        let response;
        try {
            const token = localStorage.getItem('jwtToken');


            if (role === 'student') {
                response = await StudentService.create({firstName, lastName, email, password}, token);
                navigate(`/students`);
            } else {
                response = await TeacherService.create({firstName, lastName, email, password}, token);
                navigate(`/teachers`);
            }

            setFirstName('');
            setLastName('');
            setEmail('');
            setPassword('');
        } catch (error) {
            console.error('Error creating user:', error.response.data);
            setError(error.response.data);
        } finally {
            setIsLoading(false);
        }
    };

    console.log(error)
    return (
        <div>
            <h2>New User</h2>
            <form onSubmit={handleCreateUser}>
                <input
                    type="text"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    required
                /><br/>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                /><br/>
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                /><br/>
                <div>
                    <label>
                        <input
                            type="radio"
                            value="student"
                            checked={role === 'student'}
                            onChange={() => setRole('student')}
                        />
                        Student
                    </label>
                    <label>
                        <input
                            type="radio"
                            value="teacher"
                            checked={role === 'teacher'}
                            onChange={() => setRole('teacher')}
                        />
                        Teacher
                    </label>
                </div>
                {isLoading ? (
                    <button type="button" disabled>Loading...</button>
                ) : (
                    <button type="submit">Create</button>
                )}\
                {
                    error.map((err, index) => (
                        <p key={index}>{err.message}</p>
                    ))
                }
            </form>
        </div>
    );
};

export default CreateUser;
