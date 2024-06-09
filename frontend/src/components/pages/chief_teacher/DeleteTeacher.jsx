import React, {useEffect, useState} from 'react';
import SubjectService from '../../../services/SubjectService';

const DeleteTeacher = () => {
    const [subjectsWithTeachers, setSubjectsWithTeachers] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchSubjectsWithTeachers = async () => {
            setIsLoading(true);
            try {
                const response = await SubjectService.getAll(token);
                // Відфільтруємо предмети так, щоб показувалися лише ті, у яких є вчителі
                const subjectsWithTeachers = response.filter(subject => subject.teacher !== null);
                setSubjectsWithTeachers(subjectsWithTeachers);
            } catch (error) {
                console.error('Error fetching subjects with teachers:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchSubjectsWithTeachers().then();
    }, [token]);

    const handleDeleteTeacher = async () => {
        if (!selectedSubject) return;
        try {
            await SubjectService.deleteTeacher(selectedSubject.id, token);
            setSelectedSubject('');
            // Опціонально: оновити список предметів після видалення вчителя
            const updatedSubjectsWithTeachers = subjectsWithTeachers.filter(subject => subject.id !== selectedSubject.id);
            setSubjectsWithTeachers(updatedSubjectsWithTeachers);
        } catch (error) {
            console.error('Error deleting teacher from subject:', error);
        }
    };

    return (
        <div className="delete-container">
            <h2>Delete Teacher from Subject</h2>
            <div className="subject-select">
                <label>Select Subject:</label>
                {isLoading ? (
                    <div>Loading...</div>
                ) : (
                    subjectsWithTeachers.map((subject) => (
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
            <button onClick={handleDeleteTeacher} disabled={!selectedSubject}>Delete Teacher</button>
        </div>
    );
};

export default DeleteTeacher;
