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
import SubjectWithTeacher from "./components/common/subject/SubjectWithTeacher";
import TeacherListPage from "./components/pages/teacher/TeacherListPage";
import TeacherPage from "./components/pages/teacher/TeacherPage";
import Subjects from "./components/pages/subject/Subjects";
import Admin from "./components/pages/chief_teacher/Admin";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/admin" element={<Admin/>}/>
                <Route path="/teachers/getById/:id" element={<TeacherPage/>}/>
                <Route path="/teachers" element={<TeacherListPage/>}/>
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
                <Route path={"/subjects"} element={<Subjects/>}/>

                <Route path="/test" element={<SubjectWithTeacher
                    subjectResponse={
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
