import {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectService from "../../../services/SubjectService";
import SubjectWithTeacher from "../../common/subject/SubjectWithTeacher";
import UserMap from "../../common/user/UserMap";
import MarkService from "../../../services/MarkService";
import StudentWithMarkFromThisSubjectMap from "../../common/marks/StudentWithMarkFromThisSubjectMap";

const Subject = () => {
    const [canMark, setIfCanMark] = useState(false)
    const [subject, setSubject] = useState(null);
    const [marks, setMarks] = useState(null);
    const [loadingSubject, setLoadingSubject] = useState(true);
    const [loadingMarks, setLoadingMarks] = useState(true);
    const subjectId = 1

    useEffect(() => {

        const token = localStorage.getItem("jwtToken");

        const fetchMarks = async () => {
            try {
                const response = await MarkService.getBySubjectId(subjectId, token)
                setMarks(response);
                setIfCanMark(true);
            } catch (error) {
                setIfCanMark(false);
            } finally {
                setLoadingMarks(false);
            }
        }

        const fetchSubject = async () => {
            try {
                const response = await SubjectService.getById(subjectId, token);
                setSubject(response);
            } finally {
                setLoadingSubject(false);
            }
        };

        fetchSubject().then(() => console.log("End work with subjects"));
        fetchMarks().then(() => console.log("End work with marks"))
    }, []);

    if (loadingSubject || loadingMarks) {
        return <div>Loading...</div>; // Loading indicator
    }

    const role = localStorage.getItem('role');
    console.log(role);
    console.log(subject);
    console.log(marks);

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
                </div>
            <div>
                {canMark ?
                    <StudentWithMarkFromThisSubjectMap
                        marks={marks}/>
                    :
                    <UserMap
                        users={subject.students}
                    />
                }
            </div>
        </div>
    );
};

export default Subject;
