import React, {useEffect, useState} from 'react';
import '../../../assets/styles/StudentsList.css';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import TeacherService from '../../../services/TeacherService';
import TeacherList from '../../common/teacher/TeacherList';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';

const TeacherListPage = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [teacherList, setTeacherList] = useState([]);
    const [filteredTeachers, setFilteredTeachers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAllTeachers = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await TeacherService.getAll(token);
                setTeacherList(response);
                setFilteredTeachers(response);
            } catch (error) {
                console.error('Error fetching teachers data:', error);
                setError('Error fetching teachers data. Please try again.');
            }
        };

        fetchAllTeachers();
    }, []);

    const handleSearch = async () => {
        try {
            const token = localStorage.getItem('jwtToken');
            if (firstName || lastName) {
                const data = await TeacherService.getByName(firstName, lastName, token);
                setFilteredTeachers(data);
            } else {
                setFilteredTeachers(teacherList);
            }
            setError(null);
        } catch (error) {
            console.error('Error fetching data:', error);
            setError('Error fetching data. Please try again.');
            setFilteredTeachers([]);
        }
    };

    return (
        <div>
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
                <Button onClick={handleSearch}>
                    Search
                </Button>
                {error && <div style={{ color: 'red' }}>{error}</div>}
                <TeacherList teachers={filteredTeachers} />
            </Box>
        </div>
    );
};

export default TeacherListPage;
