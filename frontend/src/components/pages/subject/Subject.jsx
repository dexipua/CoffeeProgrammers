import React, {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectService from "../../../services/SubjectService";
import MarkService from "../../../services/MarkService";
import {useParams} from "react-router-dom";
import SubjectWithTeacherForProfile from "../../common/subject/SubjectWithTeacherForProfile";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import StudentListForProfile from "../../common/student/StudentListForProfile";
import MarkTableForSubject from "../../common/mark/MarkTableForSubject";
import AddStudentButton from "../../common/subject/AddStudentButton";
import ChangeSubjectNameButton from "../../common/subject/ChangeSubjectNameButton";

const Subject = () => {
    const {id} = useParams();

    const [canGetMark, setCanGetMark] = useState(false);
    const [subject, setSubject] = useState(null);
    const [studentsWithMarks, setStudentsWithMarks] = useState([]);
    const [loadingSubject, setLoadingSubject] = useState(true);
    const [loadingMarks, setLoadingMarks] = useState(true);

    useEffect( () => {
        const token = localStorage.getItem("jwtToken");

        const fetchMarks = async () => {
            try {
                const response = await MarkService.getBySubjectId(id, token);
                setStudentsWithMarks(response);
                setCanGetMark(true);
            } catch (error) {
                setCanGetMark(false);
            } finally {
                setLoadingMarks(false);
            }
        };

        const fetchSubject = async () => {
            try {
                const response = await SubjectService.getById(id, token);
                setSubject(response);
            } finally {
                setLoadingSubject(false);
            }
        };

        const fetchData = async () => {
            await fetchSubject();
            await fetchMarks();
        };

         fetchData().then(() => console.log("End work with data"));
    }, [id]);
    const handleNameChange = (newName) => {
        setSubject({ ...subject, name: newName });
    };

    const handleStudentDelete = (studentId) => {
        const updatedStudents = subject.students.filter(student => student.id !== studentId);
        const updatedStudentsWithMarks = studentsWithMarks.filter(student => student.id !== studentId);
        setSubject({ ...subject, students: updatedStudents });
        setStudentsWithMarks(updatedStudentsWithMarks)
    };


    const marksByStudentId = {};
    studentsWithMarks.forEach(({studentResponseSimple, marks}) => {
        marksByStudentId[studentResponseSimple.id] = marks;
    });

    const studentsWithGradesOrEmpty = subject?.students?.map(student => ({
        student, marks: marksByStudentId[student.id] || [],
    }));

    if (loadingSubject || loadingMarks) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <ButtonAppBar/>
            <Box mt="80px" ml="60px">
                <SubjectWithTeacherForProfile subject={subject}/>
            </Box>


            {canGetMark && (
                <Box
                    width={300}
                    mt={4}
                    ml="60px"
                    p={2}
                    sx={{
                        border: '1px solid #ddd',
                        borderRadius: '8px',
                        backgroundColor: '#ffffff'
                    }}
                >
                    <Typography mb="5px" variant="h6" component="h3">
                        Manage
                    </Typography>

                    <Box mb="10px">
                        <ChangeSubjectNameButton
                            subjectId={subject.id}
                            onNameChange={handleNameChange}
                        />
                    </Box>

                    <Box>
                        <AddStudentButton subjectId={subject.id}/>
                    </Box>
                </Box>
            )}

            <Box
                mt={4}
                ml="60px"
                mr="60px"
                p={2}
                sx={{
                    border: '1px solid #ddd',
                    borderRadius: '8px',
                    backgroundColor: '#ffffff'
                }}
            >
                <Box mb={2}>
                    <Typography variant="h6" component="h3">Students</Typography>
                </Box>
                {!canGetMark ? (
                    <StudentListForProfile students={subject.students}/>
                ) : (
                    <MarkTableForSubject
                        subjectId={subject.id}
                        students={studentsWithGradesOrEmpty}
                        onStudentDelete={handleStudentDelete}
                    />
                )}
            </Box>
        </>
    );
};

export default Subject;
