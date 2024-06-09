import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import '../../../assets/styles/StudentsList.css';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import StudentSimpleMap from '../../common/user/StudentSimpleMap';
import Box from '@mui/material/Box';

const StudentsList = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [studentsList, setStudentsList] = useState([]);
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [error, serError] = useState()

    useEffect(() => {
        const fetchAllStudents = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await StudentService.getAll(token);
                setStudentsList(response);
                setFilteredStudents(response);
            } catch (error) {
                console.error('Error fetching students data:', error);
            }
        };

        fetchAllStudents().then();
    }, []);

    useEffect(() => {
        if (firstName || lastName) {
            const filtered = studentsList.filter(student =>
                student.firstName.toLowerCase().includes(firstName.toLowerCase()) &&
                student.lastName.toLowerCase().includes(lastName.toLowerCase())
            );
            setFilteredStudents(filtered);
        } else {
            setFilteredStudents(studentsList);
        }
    }, [firstName, lastName, studentsList]);

    return (
        <div className="students-list">
            <ButtonAppBar />
            <Box mt={9}>
                <input
                    type="text"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder="First Name"
                />
                <input
                    type="text"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder="Last Name"
                />
                {error && <div style={{ color: 'red' }}>{error}</div>}
                <StudentSimpleMap users={filteredStudents} />
            </Box>
        </div>
    );
};

export default StudentsList;
