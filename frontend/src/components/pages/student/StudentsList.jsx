import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import {Link} from "react-router-dom";
import '../../../assets/styles/StudentsList.css'

const StudentsList = () => {
    const [studentsList, setStudentsList] = useState([{
        id: -1,
        firstName: "",
        lastName: ""
    }]);

    useEffect(() => {
        const fetchStudentsAll = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await StudentService.getAll(token);
                setStudentsList(response);
            } catch (error) {
                console.error('Error fetching students data:', error);
            }
        };

        fetchStudentsAll()
            .then(() => {
                console.log('Data fetched successfully');
            })
    }, []);

    return (
        <div className="students-list">
            <h2>Students List</h2>
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>
                {studentsList.map((student, index) => (
                    <tr key={student.id}>
                        <td>{index + 1}</td>
                        <td>
                            <Link to={`/students/getById/${student.id}`}>
                                 {student.lastName} {student.firstName}
                            </Link>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default StudentsList;