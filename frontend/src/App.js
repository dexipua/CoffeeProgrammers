import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './components/pages/home/Home';
import Login from './components/pages/user/Login';
import StudentProfile from "./components/pages/student/StudentProfile";
import UserListAll from "./components/pages/user/Users";
import Users from "./components/pages/user/Users";
import Subject from "./components/pages/subject/Subject";
import TeacherProfile from "./components/pages/teacher/TeacherProfile";
import SubjectList from "./components/pages/subject/Subjects";
import Admin from "./components/pages/chief_teacher/Admin";
import NotFound from "./components/pages/not_found/NotFound";
import Bookmark from "./components/pages/Bookmark/Bookmark";

function App() {
    return (
            <Router>

                <Routes>
                    <Route path="" element={<Login/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/admin_panel" element={<Admin/>}/>
                    <Route path="/teachers/:id" element={<TeacherProfile/>}/>
                    <Route path="/students/:id" element={<StudentProfile/>}/>
                    <Route path="/subjects/:id" element={<Subject/>}/>
                    <Route path={"/subjects"} element={<SubjectList/>}/>
                    <Route path={"/users"} element={<Users/>}/>
                    <Route path="/community" element={<UserListAll/>}/>
                    <Route path={"/bookmark"} element={<Bookmark/>}/>>
                    <Route path="*" element={<NotFound/>}/>

                </Routes>
            </Router>
    );
}

export default App;
