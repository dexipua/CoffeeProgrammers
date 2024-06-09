import {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectService from "../../../services/SubjectService";
import SubjectWithTeacher from "../../common/subject/SubjectWithTeacher";
import MarkService from "../../../services/MarkService";
import UserMap from "../../common/user/UserMap";

const Subject = () => {
    const [error, setError] = useState(false);
    const subjectId = 1;
    const [subject, setSubject] = useState(null);
    const [marks, setMarks] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchMarks = async () => {
            try {
                const response = await MarkService.getBySubjectId(subjectId, token);
                    const data = await response.json();
                    setMarks(data);
                    setError(false)
            } catch (error) {}
        };

        const fetchSubject = async () => {
            try {
                const response = await SubjectService.getById(subjectId, token);
                setSubject(response);
            } catch (error) {
                setError(true); // Set error message
            } finally {
                setLoading(false);
            }
        };

        fetchMarks();
        fetchSubject();
    }, []);

    if (loading) {
        return <div>Loading...</div>; // Loading indicator
    }

    const role = localStorage.getItem('role');
    console.log(role);
    console.log(subject);
    console.log(error)

    return (
        <div>
            <ButtonAppBar/>
                <div>
                    <SubjectWithTeacher //TODO
                        subjectResponse={{
                            id: subject.id,
                            name: subject.name,
                            teacher: subject.teacher
                        }}
                    />
                    <UserMap
                        users={subject.students}
                    />
                </div>
        </div>
    );
}

export default Subject;
