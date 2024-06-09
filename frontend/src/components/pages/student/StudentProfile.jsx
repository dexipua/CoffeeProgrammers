import React, {useEffect, useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import StudentService from "../../../services/StudentService";
import '../../../assets/styles/StudentProfile.css';
import ButtonAppBar from "../../layouts/ButtonAppBar";

const StudentProfile = () => {
    const { id } = useParams(); // Retrieve the id from the route parameters
    const [student, setStudent] = useState({
        id: -1,
        firstName: "",
        lastName: "",
        email: "",
        averageMark: 0.0,
        subjects: []
    });

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        const fetchStudentById = async () => {
            try {
                const response = await StudentService.getById(id, token);
                setStudent(response);
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        };

        fetchStudentById().then(() => {
            console.log('Student data fetched successfully');
        });
    }, [id]);

    // Render component
    return (
        <div className="student-profile">
            <ButtonAppBar />
            <h2>Student Profile</h2>
            <p><strong>Name:</strong> {student.firstName} {student.lastName}</p>
            <p><strong>Email:</strong> {student.email}</p>
            <p><strong>Average Mark:</strong> {student.averageMark}</p>
            <p><strong>Subjects:</strong></p>
            <ul>
                {student.subjects.map((subject) => (
                    <Link to={`/subjects/getById/${subject.id}`}>
                        <li key={subject.id}>{subject.name}</li>
                    </Link>
                ))}
            </ul>
        </div>
    );
};

export default StudentProfile;
