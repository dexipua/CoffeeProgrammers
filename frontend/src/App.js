import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './components/basic/pages/Home';
import Login from './components/basic/pages/Login';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/home" element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;
