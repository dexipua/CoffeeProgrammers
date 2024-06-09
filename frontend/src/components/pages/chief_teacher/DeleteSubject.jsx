import React, {useEffect, useState} from 'react';
import SubjectService from '../../../services/SubjectService';

const DeleteSubject = () => {
    const [subjects, setSubjects] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchSubjects = async () => {
            setIsLoading(true);
            try {
                const response = await SubjectService.getAll(token);
                setSubjects(response);
            } catch (error) {
                console.error('Error fetching subjects:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchSubjects();
    }, [token]);

    const handleDeleteSubject = async () => {
        if (!selectedSubject) return;

        try {
            await SubjectService.delete(selectedSubject, token);
            // Оновити список предметів після видалення
            const updatedSubjects = subjects.filter(subject => subject.id !== selectedSubject);
            setSubjects(updatedSubjects);
            // Скинути обраний предмет після видалення
            setSelectedSubject('');
        } catch (error) {
            console.error('Error deleting subject:', error);
        }
    };

    return (
        <div>
            <h2>Delete Subject</h2>
            {isLoading ? (
                <div>Loading...</div>
            ) : (
                <div>
                    {subjects.map(subject => (
                        <div key={subject.id}>
                            <label>
                                <input
                                    type="radio"
                                    value={subject.id}
                                    checked={selectedSubject === subject.id}
                                    onChange={() => setSelectedSubject(subject.id)}
                                />
                                {subject.name}
                            </label>
                        </div>
                    ))}
                    <button onClick={handleDeleteSubject} disabled={!selectedSubject}>
                        Delete Subject
                    </button>
                </div>
            )}
        </div>
    );
};

export default DeleteSubject;
