// Admin.jsx
import * as React from 'react';
import {useState} from 'react';
import VerticalTabs from './adminPanel/VerticalTabs'; // Імпорт компонента VerticalTabs
import ApplicationBar from "../../layouts/ApplicationBar";
import CreateUser from "./adminPanel/CreateUser";
import DeleteUser from "./adminPanel/DeleteUser";
import CreateSubject from "./adminPanel/CreateSubject";
import DeleteSubject from "./adminPanel/DeleteSubject";
import DeleteTeacherFromSubject from "./adminPanel/DeleteTeacherFromSubject";
import SetTeacher from "./adminPanel/SetTeacher";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

const Admin = () => {
    const [currentForm, setCurrentForm] = useState('');

    const renderForm = () => {
        switch (currentForm) {
            case 'createUser':
                return <CreateUser />;
            case 'deleteUser':
                return <DeleteUser />;
            case 'createSubject':
                return <CreateSubject />;
            case 'deleteSubject':
                return <DeleteSubject />;
            case 'addTeacher':
                return <SetTeacher />;
            case 'deleteTeacher':
                return <DeleteTeacherFromSubject />;
            default:
                return null;
        }
    };

    return (
        <>
            <ApplicationBar />
            <Box align="center" style={{ marginTop: '80px' }}>
                <Typography mb={2} variant="h4" align="center">
                    Admin Panel
                </Typography>
                    <VerticalTabs setCurrentForm={setCurrentForm} />

                        {renderForm()}



            </Box>
        </>
    );
};
export default Admin;
