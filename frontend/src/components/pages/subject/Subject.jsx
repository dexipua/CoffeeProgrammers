import React, {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectService from "../../../services/SubjectService";
import MarkService from "../../../services/MarkService";
import {useParams} from "react-router-dom";
import SubjectWithTeacherForProfile from "../../common/subject/SubjectWithTeacherForProfile";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import StudentListForProfile from "../../common/student/StudentListForProfile";
import LabTabsForSubjectProfile from "../../layouts/LabTabsForSubjectProfile";

const Subject = () => {
    const {id} = useParams();

    const [canMark, setIfCanMark] = useState(false)
    const [subject, setSubject] = useState(null);
    const [studentsWithMarks, setStudentsWithMarks] = useState([]);
    const [loadingSubject, setLoadingSubject] = useState(true);
    const [loadingMarks, setLoadingMarks] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchMarks = async () => {
            try {
                const response = await MarkService.getBySubjectId(id, token);
                setStudentsWithMarks(response);
                setIfCanMark(true);
            } catch (error) {
                setIfCanMark(false);
            } finally {
                setLoadingMarks(false);
            }
        }

        const fetchSubject = async () => {
            try {
                const response = await SubjectService.getById(id, token);
                setSubject(response);
            } finally {
                setLoadingSubject(false);
            }
        };

        const fetchData = async () => {
            await fetchSubject().then(() => console.log("End work with subjects"));
            await fetchMarks().then(() => console.log("End work with marks"))
        };


        fetchData().then(() => console.log("End work with data"))
    }, [id]);


    if (loadingSubject && loadingMarks) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <ButtonAppBar/>
            <Box mt="80px" ml="60px">
                <Typography variant="h5" component="h2" sx={{marginBottom: 2}}>Subject</Typography>
                <SubjectWithTeacherForProfile
                    subject={{
                        id: subject.id,
                        name: subject.name,
                        teacher: subject.teacher
                    }}
                />
            </Box>

            <Box mt={4} ml="60px" mr="60px">
                {!canMark ? (
                    <>
                        <Typography variant="h6" component="h2" sx={{marginBottom: 2}}>Students</Typography>
                        <StudentListForProfile students={subject.students}/>
                    </>
                ) : (
                    <LabTabsForSubjectProfile
                        students={subject.students}
                        studentsWithMarks={studentsWithMarks}
                    />
                )}
            </Box>
        </div>
    );
};

export default Subject;
