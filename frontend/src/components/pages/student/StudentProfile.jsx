import React, {useEffect, useState} from "react";
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import AccountContainer from "../../common/user/AccountContainer";
import {Grid} from "@mui/material";
import AverageMarkView from "../../common/student/AverageMarkView";
import StudentService from "../../../services/StudentService";
import {useParams} from "react-router-dom";
import Loading from "../../layouts/Loading"; // Імпортуємо компонент Loading

const StudentProfile = () => {
    const { id } = useParams();

    const [student, setStudent] = useState(null);
    const [isThisStudent, setIsThisStudent] = useState(false);
    const [loading, setLoading] = useState(true);

    const role = localStorage.getItem('role');
    const token = localStorage.getItem('jwtToken');
    const roleId = localStorage.getItem('roleId');

    useEffect(() => {
        const fetchStudentById = async () => {
            try {
                const response = await StudentService.getById(id, token);
                setStudent(response);
                setIsThisStudent(id === roleId && role === "STUDENT");
            } catch (error) {
                console.error('Error fetching student data:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchStudentById();
    }, [id, role, roleId, token]);

    const handleNameUpdate = (newFirstName, newLastName) => {
        setStudent(prevStudent => ({
            ...prevStudent,
            firstName: newFirstName,
            lastName: newLastName
        }));
    };

    if (loading) {
        return <Loading />;
    }

    return (
        <>
            <ApplicationBar />
            <Box mt="80px" ml="60px" mr="60px">
                <Grid container spacing={2} alignItems="flex-start">
                    <Grid item xs={12} sm={6} md={4}>
                        <AccountContainer
                            edit={isThisStudent}
                            topic={"Student"}
                            user={student}
                            onNameUpdate={handleNameUpdate}
                        />
                        {isThisStudent && (
                            <AverageMarkView
                                averageMark={student.averageMark}
                            />
                        )}
                    </Grid>

                    <Grid item xs={12} sm={6} md={8}>
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
                                mt: 2
                            }}
                        >
                            <>
                            <Typography mt="10px" variant="h6" component="h3">Subjects</Typography>
                            <Box
                                sx={{
                                    width: '100%',
                                    display: 'flex',
                                    justifyContent: 'center',
                                }}
                            >
                                <SubjectWithTeacherList subjects={student.subjects} />
                            </Box>
                            </>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
}

export default StudentProfile;
