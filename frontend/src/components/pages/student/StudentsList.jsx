import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import '../../../assets/styles/StudentsList.css'
import ButtonAppBar from "../../layouts/ButtonAppBar";
import StudentSimpleMap from "../../common/user/StudentSimpleMap";

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
            <ButtonAppBar/>
            <h2>Students List</h2>
            <StudentSimpleMap
                users={studentsList}
            />
        </div>
    );
};

export default StudentsList;