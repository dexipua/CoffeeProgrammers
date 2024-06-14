import React, {useEffect, useState} from "react";
import ApplicationBar from "../../layouts/ApplicationBar";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import AccountContainer from "../../common/user/AccountContainer";
import {Grid} from "@mui/material";
import StudentService from "../../../services/StudentService";
import {useParams} from "react-router-dom";
import Loading from "../../layouts/Loading";
import TeacherService from "../../../services/TeacherService";
import StudentTable from "../../common/student/StudentTable";
import TeacherSubjects from "../../common/teacher/TeacherSubjects"; // Імпортуємо компонент Loading

const TeacherProfile = () => {
    const {id} = useParams();

    const [teacher, setTeacher] = useState(null);
    const [studentsOfThisTeacher, setStudentsOfThisTeacher] = useState([])
    const [isThisStudent, setIsThisStudent] = useState(false);
    const [loading, setLoading] = useState(true);

    const role = localStorage.getItem('role');
    const token = localStorage.getItem('jwtToken');
    const roleId = localStorage.getItem('roleId');

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const [teacherResponse, studentsResponse] =
                    await Promise.all(
                        [
                            TeacherService.getById(id, token),
                            StudentService.getByTeacherId(id, token)
                        ]
                    );
                setTeacher(teacherResponse);
                setStudentsOfThisTeacher(studentsResponse);
            } catch (error) {
                console.error('Error fetching data:', error);
            } finally {
                setLoading(false);
            }
        };

        setIsThisStudent(id === roleId && role !== "STUDENT");
        fetchData();
    }, [id, role, roleId, token]);

    const handleNameUpdate = (newFirstName, newLastName) => {
        setTeacher(prevStudent => ({
            ...prevStudent,
            firstName: newFirstName,
            lastName: newLastName
        }));
    };

    if (loading) {
        return <Loading/>;
    }

    return (
        <>
            <ApplicationBar/>
            <Box mt="80px" ml="60px" mr="60px">
                <Grid container spacing={2} alignItems="flex-start">
                    <Grid item xs={12} sm={6} md={4}>
                        <AccountContainer
                            edit={isThisStudent}
                            topic={"Teacher"}
                            user={teacher}
                            onNameUpdate={handleNameUpdate}
                        />

                        <TeacherSubjects
                            subjects={teacher.subjects}
                        />
                    </Grid>

                    <Grid item xs={12} sm={6} md={8}>
                        <Box
                            sx={{
                                width: '750px',
                                border: '1px solid #ddd',
                                borderRadius: '8px',
                                backgroundColor: '#ffffff',
                                textAlign: 'center',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                mt: 2
                            }}
                        >
                            <>
                                <Typography mt="10px" mb="10px" variant="h6" component="h3">Students</Typography>
                                <Box
                                    sx={{
                                        width: '100%',
                                        display: 'flex',
                                        justifyContent: 'center',
                                    }}
                                >
                                    <StudentTable students={studentsOfThisTeacher}/>
                                </Box>
                            </>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
}

export default TeacherProfile;
