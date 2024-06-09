import React, {useEffect, useState} from 'react';
import '../../../assets/styles/StudentsList.css'
import ButtonAppBar from "../../layouts/ButtonAppBar";
import TeacherService from "../../../services/TeacherService";
import TeacherList from "../../common/teacher/TeacherList";

const TeacherListPage = () => {
    const [teacherList, setTeacherList] = useState([{
        id: -1,
        firstName: "",
        lastName: ""
    }]);

    useEffect(() => {
        const fetchStudentsAll = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await TeacherService.getAll(token);
                setTeacherList(response);
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
            <h2>Teacher List</h2>
            <TeacherList
                teachers={teacherList}/>
        </div>
    );
};

export default TeacherListPage;