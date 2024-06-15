import React, {useEffect, useState} from "react";
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import SubjectService from "../../../services/SubjectService";
import MarkService from "../../../services/MarkService";
import {useParams} from "react-router-dom";
import SubjectWithTeacherForProfile from "../../common/subject/SubjectWithTeacherForProfile";
import StudentsBox from "../../common/subject/StudentsBox";
import ManageBox from "../../common/subject/ManageBox";
import Loading from "../../layouts/Loading";
import StudentService from "../../../services/StudentService";
import SubjectSchedule from "../schedule/SubjectSchedule";
import SubjectTabs from "../../common/subject/SubjectTabs";
import Box from "@mui/material/Box";

const Subject = () => {
    const {id} = useParams();

    const [isTeacherOfThisSubject, setIsTeacherOfThisSubject] = useState(false);
    const [subject, setSubject] = useState(null);
    const [studentsWithMarks, setStudentsWithMarks] = useState([]);
    const [loadingSubject, setLoadingSubject] = useState(true);
    const [loadingMarks, setLoadingMarks] = useState(true);

    const token = localStorage.getItem("jwtToken");

    useEffect(() => {
        const fetchMarks = () => {
            MarkService.getBySubjectId(id, token)
                .then(response => {
                    setStudentsWithMarks(response);
                    setIsTeacherOfThisSubject(true);
                })
                .catch(() => {
                    setIsTeacherOfThisSubject(false);
                })
                .finally(() => {
                    setLoadingMarks(false);
                });
        };

        const fetchSubject = () => {
            SubjectService.getById(id, token)
                .then(response => {
                    setSubject(response);
                })
                .finally(() => {
                    setLoadingSubject(false);
                });
        };

        const fetchData = async () => {
            await fetchSubject();
            await fetchMarks();
        };

        fetchData();
    }, [id]);

    const handleNameChange = (newName) => {
        setSubject(prevSubject => ({...prevSubject, name: newName}));
    };

    const handleStudentAdd = async (studentId) => {
        const response = await StudentService.getById(studentId, token)
        setSubject((prevSubject) => ({
            ...prevSubject,
            students: [...prevSubject.students, response],
        }));
    };
    const handleStudentDelete = (studentId) => {
        setSubject(prevSubject => ({
            ...prevSubject,
            students: prevSubject.students.filter(student => student.id !== studentId)
        }));
    };

    const handleMarkCreate = (studentId, newMark) => {
        setStudentsWithMarks(prevState => {
            const studentExists = prevState.some(studentMarkData => studentMarkData.studentResponseSimple.id === studentId);

            if (studentExists) {
                return prevState.map(studentMarkData => {
                    if (studentMarkData.studentResponseSimple.id === studentId) {
                        const updatedMarks = studentMarkData.marks ? [...studentMarkData.marks, newMark] : [newMark];
                        return {
                            ...studentMarkData,
                            marks: updatedMarks
                        };
                    }
                    return studentMarkData;
                });
            } else {
                const newStudentMarkData = {
                    studentResponseSimple: {id: studentId},
                    marks: [newMark]
                };
                return [...prevState, newStudentMarkData];
            }
        });
    };

    const handleMarkUpdate = (studentId, updatedMark) => {
        setStudentsWithMarks(prevState => prevState.map(studentMarkData => {
            if (studentMarkData.studentResponseSimple.id === studentId) {
                const updatedMarks = studentMarkData.marks.map(mark => mark.id === updatedMark.id ? updatedMark : mark);
                return {
                    ...studentMarkData,
                    marks: updatedMarks
                };
            }
            return studentMarkData;
        }));
    };

    const handleMarkDelete = (studentId, markIdToDelete) => {
        setStudentsWithMarks(prevState => prevState.map(studentMarkData => {
            if (studentMarkData.studentResponseSimple.id === studentId) {
                const updatedMarks = studentMarkData.marks.filter(mark => mark.id !== markIdToDelete);
                return {
                    ...studentMarkData,
                    marks: updatedMarks
                };
            }
            return studentMarkData;
        }));
    };

    const getStudentsWithGradesOrEmpty = () => {
        return subject?.students?.map(student => {
            const studentMarkData = studentsWithMarks.find(({studentResponseSimple}) => studentResponseSimple.id === student.id);
            return {student, marks: studentMarkData ? studentMarkData.marks : []};
        }) || [];
    };

    if (loadingSubject || loadingMarks) {
        return <Loading/>;
    }

    return (
        <>
            <ApplicationBar/>
            <Box
                mt="80px"
                display="flex"
                justifyContent="center"
                alignItems="center"
            >
                <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    flexDirection="row"
                    gap={3}
                >
                    <SubjectWithTeacherForProfile subject={subject}/>
                    {isTeacherOfThisSubject && (
                        <ManageBox
                            subjectId={subject.id}
                            onNameChange={handleNameChange}
                            onStudentAdd={handleStudentAdd}
                        />
                    )}
                </Box>
            </Box>
            <Box display="flex" flexDirection="column" alignItems="center">
                <SubjectTabs
                    tab2={
                        <SubjectSchedule
                            isTeacherOfThisSubject={isTeacherOfThisSubject}
                            subjectId={subject.id}/>
                    }
                    tab1={
                        <StudentsBox
                            isTeacherOfThisSubject={isTeacherOfThisSubject}
                            subjectId={subject.id}
                            studentsWithGradesOrEmpty={getStudentsWithGradesOrEmpty()}
                            subjectStudents={subject.students}
                            onStudentDelete={handleStudentDelete}
                            onMarkCreate={handleMarkCreate}
                            onMarkUpdate={handleMarkUpdate}
                            onMarkDelete={handleMarkDelete}
                        />
                    }
                />
            </Box>
        </>
    );
};

export default Subject;
