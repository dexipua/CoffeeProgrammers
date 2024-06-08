import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import StudentService from "../../../services/StudentService";
import '../../../assets/styles/CreateUser.css'

const CreateUser = () => {
    const [firstName, setFirstname] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [role, setRole] = useState('')

    // const fetchCreate = async (role) => {
    //     const token = localStorage.getItem('jwtToken');
    //     try {
    //         const response =
    //             role === "student" ?
    //                 (await StudentService.create(
    //                     {
    //                         firstName,
    //                         lastName,
    //                         email,
    //                         password
    //                     },
    //                     token
    //                 ))
    //                 :
    //                 (await TeacherService.create(
    //                     {
    //                         firstName,
    //                         lastName,
    //                         email,
    //                         password
    //                     },
    //                     token
    //                 ))
    //
    //
    //     } catch (error) {
    //         console.error('Error fetching  data:', error);
    //     }
    // };
    // const student = ({id, first_name, last_name}) => {
    //
    // }

        let navigate = useNavigate();

    const fetchCreate = async (e, role) => {
        e.preventDefault();
        const token = localStorage.getItem('jwtToken');
        try {
            const response = await StudentService.create(
                {
                    firstName,
                    lastName,
                    email,
                    password
                },
                token
            )
            navigate(`/students/getById/${response.id}`);
        } catch (error) {
            console.error('Error fetching  data:', error);
        }
    };

    return (
        <div className="create-container">
            <h2>New user</h2>
            <form onSubmit={fetchCreate}>
                <input
                    type="text"
                    placeholder="First name"
                    value={firstName}
                    onChange={(e) =>
                        //setFirstname(e.target.value)}
                        setFirstname("Student")}
                />
                <input
                    type="text"
                    placeholder="Last name"
                    value={lastName}
                    onChange={(e) =>
                        // setLastName(e.target.value)}
                        setLastName("Student")}
                />
                <input
                    type="text"
                    placeholder="Email"
                    value={email}
                    onChange={(e) =>
                        //setEmail(e.target.value)}
                        setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        // setPassword(e.target.value)}
                        setPassword("Password1")}
                />
                <button type="submit">Create</button>
            </form>
        </div>
    );
};

export default CreateUser;
