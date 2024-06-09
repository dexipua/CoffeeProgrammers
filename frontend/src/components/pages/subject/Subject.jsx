import {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectService from "../../../services/SubjectService";
import SubjectWithTeacher from "../../common/subject/SubjectWithTeacher";
import MarkService from "../../../services/MarkService";
import StudentWithMarkFromThisSubjectMap from "../../common/marks/StudentWithMarkFromThisSubjectMap";
import {useParams} from "react-router-dom";
import StudentSimpleMap from "../../common/user/StudentSimpleMap";

const Subject = () => {
    const [canMark, setIfCanMark] = useState(false)
    const [subject, setSubject] = useState(null);
    const [marks, setMarks] = useState(null);
    const [loadingSubject, setLoadingSubject] = useState(true);
    const [loadingMarks, setLoadingMarks] = useState(true);
    const {id} = useParams();

    useEffect(() => {

        const token = localStorage.getItem("jwtToken");

        const fetchMarks = async () => {
            try {
                const response = await MarkService.getBySubjectId(id, token)
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
                const response = await SubjectService.getById(id, token);
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
                    //TODO
                    <StudentWithMarkFromThisSubjectMap
                        marks={marks}/>
                    :
                    <StudentSimpleMap
                        users={subject.students}
                    />
                }
            </div>
        </div>
    );
};

export default Subject;
