import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from "./components/Home";
import Login from "./components/Login";

function ReactRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/home" element={<Home/>} />
                <Route path="/" element={<Login/>} />
            </Routes>
        </BrowserRouter>
    )
}
export default ReactRouter