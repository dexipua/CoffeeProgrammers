import React, {useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import StudentService from "../../../services/StudentService";
import '../../../assets/styles/StudentProfile.css'

const StudentProfile = () => {
    const {id} = useParams(); // Отримання значення id з параметрів маршруту
    const [student, setStudent] = React.useState({
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

        fetchStudentById()
            .then(() => {
                console.log('Student data fetched successfully');
            })
    }, [id]);

    // Рендер компонента
    return (
        <div className="student-profile">
            <h2>Student Profile</h2>
            <p><strong>Name:</strong> {student.firstName} {student.lastName}</p>
            <p><strong>Email:</strong> {student.email}</p>
            <p><strong>Average Mark:</strong> {student.averageMark}</p>
            <p><strong>Subjects:</strong></p>
            <ul>
                {student.subjects.map((subject, index) => (
                    <li key={index}>{subject}</li>
                ))}
            </ul>
            <div className="marks-link">
                <Link to={`/marks/getAllByStudentId/${id}`}>
                    View Marks
                </Link>
            </div>
        </div>
    );
};

export default StudentProfile;
