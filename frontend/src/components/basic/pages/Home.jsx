import React, {useEffect, useState} from 'react';
import axios from 'axios';

const Home = () => {
    const [teachers, setTeachers] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [studentCount, setStudentCount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);


    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        console.log(token)
        if (!token) {
            setError('JWT token not found in localStorage');
            setLoading(false);
            return;
        }
        axios.get('http://localhost:9091/home', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then((response) => {
                console.log(response);
                setTeachers(response.data.teacherResponseSimpleList);
                setSubjects(response.data.subjectResponseAllList);
                setStudentCount(response.data.amountOfStudents);
                setLoading(false);
            })
            .catch((error) => {
                setError('Error fetching data: ' + error.message);
                console.error('Error fetching data:', error);
                setLoading(false);
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
            <h1>Teachers</h1>
            <ul>
                {teachers.map((teacher) => (
                    <li key={teacher.id}>
                        {teacher.firstName} {teacher.lastName}
                    </li>
                ))}
            </ul>

            <h1>Subjects</h1>
            <ul>
                {subjects.map((subject) => (
                    <li key={subject.id}>
                        {subject.name}
                        {subject.teacher && (
                            <span> (Taught by {subject.teacher.firstName} {subject.teacher.lastName})</span>
                        )}
                    </li>
                ))}
            </ul>

            <p>Total number of students: {studentCount}</p>
        </div>
    )
};

export default Home;
