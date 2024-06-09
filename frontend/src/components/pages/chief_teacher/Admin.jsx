// Admin.jsx
import * as React from 'react';
import {useState} from 'react';
import VerticalTabs from './VerticalTabs'; // Імпорт компонента VerticalTabs
import ButtonAppBar from "../../layouts/ButtonAppBar";
import CreateUser from "./CreateUser";
import DeleteUser from "./DeleteUser";
import CreateSubject from "./CreateSubject";
import DeleteSubject from "./DeleteSubject";
import DeleteTeacher from "./DeleteTeacher";
import AddTeacher from "./AddTeacher";

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
                return <AddTeacher />;
            case 'deleteTeacher':
                return <DeleteTeacher />;
            default:
                return null;
        }
    };

    return (
        <div>
            <ButtonAppBar />
            <div className="home-container" style={{ marginTop: '65px' }}>
                <h1>Admin Panel</h1>
                <div className="sidebar">
                    <VerticalTabs setCurrentForm={setCurrentForm} />
                </div>
                <div className="main-content">
                    {renderForm()}
                </div>
            </div>
        </div>
    );
};
export default Admin;
