import React, {useState} from 'react';
import SubjectService from '../../../services/SubjectService';

const CreateSubject = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const handleCreateSubject = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        try {
            const token = localStorage.getItem('jwtToken');
            await SubjectService.create({ name, description }, token);
            // Якщо успішно створено, очищуємо поля
            setName('');
            setDescription('');
        } catch (error) {
            console.error('Error creating subject:', error);
            setError('Failed to create subject. Please try again.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="create-subject-container">
            <h2>Create Subject</h2>
            <form onSubmit={handleCreateSubject}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                {isLoading ? (
                    <button type="button" disabled>Loading...</button>
                ) : (
                    <button type="submit">Create</button>
                )}
                {error && <div className="error-message">{error}</div>}
            </form>
        </div>
    );
};

export default CreateSubject;
