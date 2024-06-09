import React, {useEffect, useState} from 'react';
import '../../../assets/styles/StudentsList.css'
import ButtonAppBar from "../../layouts/ButtonAppBar";
import TeacherService from "../../../services/TeacherService";
import TeacherList from "../../common/teacher/TeacherList";
import Box from "@mui/material/Box";

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
            }
        };

        fetchAllTeachers();
    }, []);

    useEffect(() => {
        if (firstName || lastName) {
            const filtered = teacherList.filter(teacher =>
                teacher.firstName.toLowerCase().includes(firstName.toLowerCase()) &&
                teacher.lastName.toLowerCase().includes(lastName.toLowerCase())
            );
            setFilteredTeachers(filtered);
        } else {
            setFilteredTeachers(teacherList);
        }
    }, [firstName, lastName, teacherList]);

    const handleSearch = async () => {
        try {
            const token = localStorage.getItem('jwtToken');
            const data = await TeacherService.getByName(firstName, lastName, token);
            setFilteredTeachers(data);
            setError(null);
        } catch (error) {
            setError('Error fetching data. Please try again.');
            setFilteredTeachers([]);
        }
    };

    return (
        <div>
            <ButtonAppBar/>
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
                <TeacherList
                    teachers={filteredTeachers}/>
            </Box>
        </div>
    );
};

export default TeacherListPage;