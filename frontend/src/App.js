import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './components/pages/home/Home';
import Login from './components/pages/auth/Login';
import StudentProfile from "./components/pages/student/StudentProfile";
import StudentMarks from "./components/pages/student/StudentMarks";
import StudentsList from "./components/pages/student/StudentsList";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/home" element={<Home/>}/>
                <Route path="/students/getById/:id" element={<StudentProfile/>}/>
                <Route path="/marks/getAllByStudentId/:id" element={<StudentMarks/>}/>
                <Route path="/students/getAll" element={<StudentsList/>}/>
            </Routes>
        </Router>
    );
}

export default App;
