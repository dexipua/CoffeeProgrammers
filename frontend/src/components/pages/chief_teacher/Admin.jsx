// Admin.jsx
import * as React from 'react';
import {useEffect, useState} from 'react';
import VerticalTabs from './admin_panel/VerticalTabs'; // Імпорт компонента VerticalTabs
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import CreateUser from "./admin_panel/CreateUser";
import DeleteUser from "./admin_panel/DeleteUser";
import CreateSubject from "./admin_panel/CreateSubject";
import DeleteSubject from "./admin_panel/DeleteSubject";
import DeleteTeacherFromSubject from "./admin_panel/DeleteTeacherFromSubject";
import SetTeacher from "./admin_panel/SetTeacher";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";

const Admin = () => {
    const [currentForm, setCurrentForm] = useState('');

    const role = localStorage.getItem('role')

    const navigate = useNavigate();

    useEffect(() => {
        {role !== "CHIEF_TEACHER" && navigate("/*")}
    }, [role])
    const renderForm = () => {
        switch (currentForm) {
            case 'createUser':
                return <CreateUser/>;
            case 'deleteUser':
                return <DeleteUser/>;
            case 'createSubject':
                return <CreateSubject/>;
            case 'deleteSubject':
                return <DeleteSubject/>;
            case 'addTeacher':
                return <SetTeacher/>;
            case 'deleteTeacher':
                return <DeleteTeacherFromSubject/>;
            default:
                return null;
        }
    };

    return (
        <>
            <ApplicationBar/>
            <Box align="center" style={{marginTop: '100px'}}>
                <VerticalTabs setCurrentForm={setCurrentForm}/>
                {renderForm()}
            </Box>
        </>
    );
};
export default Admin;
