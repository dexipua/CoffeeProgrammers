import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import StudentService from "../../../services/StudentService";
import '../../../assets/styles/StudentProfile.css';
import ButtonAppBar from "../../layouts/ButtonAppBar";
import Typography from "@mui/material/Typography";
import SubjectList from "../../common/subject/SubjectList";
import Box from "@mui/material/Box";

const StudentProfile = () => {
    const {id} = useParams(); // Retrieve the id from the route parameters
    const [student, setStudent] = useState({
        id: -1,
        firstName: "",
        lastName: "",
        email: "",
        averageMark: 0.0,
        subjects: []
    });

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        const fetchStudentById = async () => {
            try {
                const response = await StudentService.getById(id, token);
                setStudent(response);
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        };

        fetchStudentById().then(() => {
            console.log('Student data fetched successfully');
        });
    }, [id]);

    return (
        <div>
            <ButtonAppBar/>
            <Box
                mt={10}
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"
            >
                <Box
                    mb={1}
                    width={300}
                    height={100}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    alignItems="center"
                    gap={2}
                    p={2}
                    sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        backgroundColor: '#FFFFFFFF',
                        transition: 'transform 0.2s, box-shadow 0.2s',
                    }}
                >
                    <Typography variant="body1">
                        <strong>Name:</strong> {student.firstName} {student.lastName}
                    </Typography>
                    <Typography variant="body1">
                        <strong>Email:</strong> {student.email}
                    </Typography>
                    <Typography variant="body1">
                        <strong>Average Mark:</strong> {student.averageMark}
                    </Typography>
                </Box>
                <Typography variant="body1">
                    <strong>Subjects:</strong>
                </Typography>
                <SubjectList subjects={student.subjects}/>
            </Box>
        </div>
    );
};

export default StudentProfile;
