import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './components/pages/home/Home';
import Login from './components/pages/user/Login';
import StudentProfile from "./components/pages/student/StudentProfile";
import StudentMarks from "./components/pages/student/StudentMarks";
import StudentsList from "./components/pages/student/StudentsList";
import CreateUser from "./components/pages/chief_teacher/CreateUser";
import Account from "./components/pages/user/Account";
import Subject from "./components/pages/subject/Subject";
import MarkTableForSubject from "./components/common/mark/MarkTableForSubject";

function App() {
    return (

        <Router>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/home" element={<Home/>}/>
                <Route path="/students/getById/:id" element={<StudentProfile/>}/>
                <Route path="/marks/getAllByStudentId/:id" element={<StudentMarks/>}/>
                <Route path="/students" element={<StudentsList/>}/>
                <Route path="/create_user" element={<CreateUser/>}/>
                <Route path="/account" element={<Account/>}/>
                <Route path="/subjects/getById/:id" element={<Subject/>}/>

                <Route path="/subject/:id" element={<Subject/>}/>
                <Route path="/test" element={<MarkTableForSubject students={
                    [
                        {
                            "studentResponseSimple": {
                                "id": 1,
                                "firstName": "Victoria",
                                "lastName": "Kornienko"
                            },
                            "marks": [
                                {
                                    "id": 1,
                                    "value": 1
                                }
                            ]
                        },
                        {
                            "studentResponseSimple": {
                                "id": 2,
                                "firstName": "Gleb",
                                "lastName": "Popov"
                            },
                            "marks": [
                                {
                                    "id": 2,
                                    "value": 12
                                },
                                {
                                    "id": 3,
                                    "value": 12
                                },
                                {
                                    "id": 4,
                                    "value": 12
                                },
                                {
                                    "id": 5,
                                    "value": 12
                                },
                                {
                                    "id": 6,
                                    "value": 12
                                },
                                {
                                    "id": 7,
                                    "value": 12
                                }
                            ]
                        }
                    ]
                }/>}/>
            </Routes>
        </Router>
    );
}

export default App;
