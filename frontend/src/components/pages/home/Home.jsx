import React, {useEffect, useState} from 'react';
import SubjectService from "../../../services/SubjectService";
import TeacherService from "../../../services/TeacherService";
import ApplicationBar from "../../layouts/ApplicationBar";
import UserList from "../../common/teacher/UserList"
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import Box from "@mui/material/Box";
import Loading from "../../layouts/Loading";
import Typography from "@mui/material/Typography";
import StudentService from "../../../services/StudentService";


const Home = () => {
    const [teachers, setTeachers] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [studentCount, setStudentCount] = useState(0);
    const [loading, setLoading] = useState(true);

    const token = localStorage.getItem('jwtToken');
    const roleId = localStorage.getItem('roleId')
    const role = localStorage.getItem('role')

    useEffect(() => {

        const fetchRoleData = async () => {
            try {
                const response = role === "STUDENT" ? (
                    await TeacherService.getAllByStudentId(roleId, token)
                ) : (
                    await StudentService.getByTeacherId(roleId, token)
                )
                setTeachers(response);
            } catch (error) {
                console.error('Error fetching teachers response:', error);
            }
        };

        const fetchSubjects = async () => {
            try {
                const response = role === "STUDENT" ? (
                    await SubjectService.getByStudentId(roleId, token)
                ) : (
                    await SubjectService.getByTeacherId(roleId, token)
                )
                setSubjects(response);
            } catch (error) {
                console.error('Error fetching subjects response:', error);
            }
        };

        const fetchStudentsCount = async () => {
            try {
                const response = await StudentService.getStudentsCount(token);
                setStudentCount(response);
            } catch (error) {
                console.error('Error fetching student count:', error);
            }
        };

        const fetchData = async () => {
            await Promise.all([fetchRoleData(), fetchSubjects(), fetchStudentsCount()]);
            setLoading(false);
        };

        fetchData()
            .then(() => {
                console.log('Home data fetched successfully');
            });
    }, []);

    if (loading) {
        return <Loading/>;
    }

    return (
        <>
            <ApplicationBar/>
            <Typography variant="h4" align="center" mt="80px">
                School
            </Typography>

            <Typography variant="h6" align="center">
                Our community have {studentCount} students!
            </Typography>
            <Box
                mx="auto"
                maxWidth="1100px"
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Box
                    mt={4}
                    width="100%"
                    display="flex"
                    justifyContent="space-between"
                    gap="16px"
                >
                    <Box
                        sx={{
                            width: '700px',
                            border: '1px solid #ddd',
                            borderRadius: '8px',
                            backgroundColor: '#ffffff',
                            textAlign: 'center',
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Typography mt="10px" variant="h6" component="h3">My Subjects</Typography>
                        <Box
                            sx={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: 'center',
                            }}
                        >
                            <SubjectWithTeacherList subjects={subjects} />
                        </Box>
                    </Box>

                    <Box
                        sx={{
                            width: '400px',
                            border: '1px solid #ddd',
                            borderRadius: '8px',
                            backgroundColor: '#ffffff',
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Typography mt="10px" variant="h6" component="h3">
                            {role === "STUDENT" ? "My Teachers:" : "My Students:"}
                        </Typography>
                        <Box
                            sx={{
                                width: '350px',
                                display: 'flex',
                                justifyContent: 'center',
                            }}
                        >
                            <UserList
                                role={role}
                                teachers={teachers} />
                        </Box>
                    </Box>
                </Box>
            </Box>
        </>
    );
};

export default Home;
