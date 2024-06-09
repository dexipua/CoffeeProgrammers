import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import TeacherService from "../../../services/TeacherService";
import StudentService from "../../../services/StudentService";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import UserData from "../../common/user/UserData";
import SubjectList from "../../common/subject/SubjectList";
import StudentSimpleMap from "../../common/user/StudentSimpleMap";
import Box from "@mui/material/Box";

const TeacherPage = () => {
    const {id} = useParams();
    const [loading, setLoading] = useState(true);
    const [loadingTwo, setLoadingTwo] = useState(true);
    const [teacher, setTeacher] = useState(null);
    const [students, setStudents] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchTeacherInformation = async () =>{
            try {
                const response = await TeacherService.getById(id, token)
                setTeacher(response);
            } catch (error) {
            } finally {
                setLoading(false);
            }
        }

        const fetchTeacherStudents = async () => {
            try {
                const response = await StudentService.getByTeacherId(id, token)
                setStudents(response);
            } catch (error) {
            } finally {
                setLoadingTwo(false);
            }
        }

        fetchTeacherInformation().then()
        fetchTeacherStudents().then()
    }, []);

    if (loading || loadingTwo) {
        return <div>Loading...</div>;
    }

    console.log(teacher)
    console.log(students)
    return (
        <div>
            <ButtonAppBar/>
            <Box mt={8}>
                <UserData
                    name={teacher.lastName + teacher.firstName}
                    email={teacher.email}/>
            </Box>
            <div>
                <SubjectList
                    subjects={teacher.subjects}/>
            </div>
            <div>
                <StudentSimpleMap
                    users={students}/>
            </div>
        </div>
    )
}
export default TeacherPage