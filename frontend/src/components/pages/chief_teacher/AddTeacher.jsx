import React, {useEffect, useState} from 'react';
import SubjectService from '../../../services/SubjectService';
import TeacherService from '../../../services/TeacherService';

const AddTeacher = () => {
    const [subjectsWithoutTeachers, setSubjectsWithoutTeachers] = useState([]);
    const [teachers, setTeachers] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState('');
    const [selectedTeacher, setSelectedTeacher] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchSubjectsWithoutTeachers = async () => {
            setIsLoading(true);
            try {
                const subjectsResponse = await SubjectService.getAll(token);
                const teachersResponse = await TeacherService.getAll(token);
                const subjectsWithoutTeachers = subjectsResponse.filter(subject => subject.teacher === null);
                setSubjectsWithoutTeachers(subjectsWithoutTeachers);
                setTeachers(teachersResponse);
            } catch (error) {
                console.error('Error fetching subjects or teachers:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchSubjectsWithoutTeachers().then();
    }, [token]);

    const handleAddTeacher = async () => {
        if (!selectedSubject || !selectedTeacher) return;
        try {
            await SubjectService.setTeacher(selectedSubject.id, selectedTeacher.id, token);
            const updatedSubjectsWithoutTeachers = subjectsWithoutTeachers.filter(subject => subject.id !== selectedSubject.id);
            setSubjectsWithoutTeachers(updatedSubjectsWithoutTeachers);
            setSelectedSubject('');
            setSelectedTeacher('');
        } catch (error) {
            console.error('Error adding teacher to subject:', error);
        }
    };

    return (
        <div className="add-container">
            <h2>Add Teacher to Subject</h2>
            <div className="subject-select">
                <label>Select Subject:</label>
                {isLoading ? (
                    <div>Loading...</div>
                ) : (
                    subjectsWithoutTeachers.map((subject) => (
                        <div key={subject.id}>
                            <input
                                type="radio"
                                id={subject.id}
                                name="subject"
                                value={JSON.stringify(subject)}
                                checked={selectedSubject.id === subject.id}
                                onChange={(e) => setSelectedSubject(JSON.parse(e.target.value))}
                            />
                            <label htmlFor={subject.id}>{subject.name}</label>
                        </div>
                    ))
                )}
            </div>
            <div className="teacher-select">
                <label>Select Teacher:</label>
                {isLoading ? (
                    <div>Loading...</div>
                ) : (
                    teachers.map((teacher) => (
                        <div key={teacher.id}>
                            <input
                                type="radio"
                                id={teacher.id}
                                name="teacher"
                                value={JSON.stringify(teacher)}
                                checked={selectedTeacher && selectedTeacher.id === teacher.id}
                                onChange={(e) => setSelectedTeacher(JSON.parse(e.target.value))}
                            />
                            <label htmlFor={teacher.id}>{teacher.lastName + " " + teacher.firstName}</label>
                        </div>
                    ))
                )}
            </div>
            <button onClick={handleAddTeacher} disabled={!selectedSubject || !selectedTeacher}>Add Teacher</button>
        </div>
    );
};
export default AddTeacher;