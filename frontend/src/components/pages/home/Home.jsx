import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";
import SubjectService from "../../../services/SubjectService";
import TeacherService from "../../../services/TeacherService";
import '../../../assets/styles/Home.css';
import ButtonAppBar from "../../layouts/ButtonAppBar";
import TeacherList from "../../common/teacher/TeacherList"
import SubjectList from "../../common/subject/SubjectList";


const Home = () => {
    const [teachers, setTeachers] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [studentCount, setStudentCount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        if (!token) {
            setError('JWT token not found in localStorage');
            setLoading(false);
            return;
        }

        const fetchTeachersAll = async () => {
            try {
                const response = await TeacherService.getAll(token);
                setTeachers(response);
            } catch (error) {
                console.error('Error fetching teachers response:', error);
                setError('Error fetching teachers response');
            }
        };

        const fetchSubjectsAll = async () => {
            try {
                const data = await SubjectService.getAll(token);
                setSubjects(data);
            } catch (error) {
                console.error('Error fetching subjects response:', error);
                setError('Error fetching subjects response');
            }
        };

        const fetchStudentsCount = async () => {
            try {
                const response = await axios.get('http://localhost:9091/home', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setStudentCount(response.data.amountOfStudents);
            } catch (error) {
                console.error('Error fetching student count:', error);
                setError('Error fetching student count');
            }
        };

        const fetchData = async () => {
            await Promise.all([fetchTeachersAll(), fetchSubjectsAll(), fetchStudentsCount()]);
            setLoading(false);
        };

        fetchData()
            .then(() => {
                console.log('Home data fetched successfully');
            });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <div>
                <ButtonAppBar/>
            </div>
            <div className="home-container">

                <header className="header">
                    <Link to={`/students/getAll`} className="students-link">
                        <strong>
                            Students list
                        </strong>
                    </Link>
                    <p>Total number of students: {studentCount}</p>
                </header>

                <h1>Home Page</h1>

                <div className="section">
                    <h2>Teachers</h2>
                    <TeacherList
                        teachers={teachers}/>
                </div>

                <div className="section">
                    <h2>Subjects</h2>
                    <SubjectList
                        subjects={subjects}/>
                </div>
            </div>
        </div>
    )
};

export default Home;