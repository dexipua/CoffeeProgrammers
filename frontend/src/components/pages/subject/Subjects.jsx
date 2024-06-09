import React, {useEffect, useState} from 'react';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import SubjectService from '../../../services/SubjectService';
import Box from '@mui/material/Box';
import SubjectList from '../../common/subject/SubjectList';
import Button from '@mui/material/Button';

const Subjects = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [subjectsList, setSubjectsList] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);
    const [error, setError] = useState(null);
    const [searchTrigger, setSearchTrigger] = useState(false);

    useEffect(() => {
        const fetchAllSubjects = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await SubjectService.getAll(token);
                setSubjectsList(response);
                setFilteredSubjects(response);
            } catch (error) {
                console.error('Error fetching subjects data:', error);
                setError('Error fetching subjects data. Please try again.');
            }
        };

        fetchAllSubjects();
    }, []);

    useEffect(() => {
        if (searchTrigger) {
            const fetchSubjectsByName = async () => {
                try {
                    const token = localStorage.getItem('jwtToken');
                    const response = await SubjectService.getByNameContaining(searchTerm, token);
                    setFilteredSubjects(response);
                } catch (error) {
                    console.error('Error fetching subjects by name:', error);
                    setError('Error fetching subjects. Please try again.');
                }
            };

            fetchSubjectsByName();
            setSearchTrigger(false); // Reset search trigger
        } else {
            setFilteredSubjects(subjectsList);
        }
    }, [searchTrigger, searchTerm, subjectsList]);

    const handleSearchClick = () => {
        setSearchTrigger(true);
    };

    return (
        <div>
            <ButtonAppBar />
            <Box mt={9}>
                <input
                    type="text"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search Subject by Name"
                />
                <Button onClick={handleSearchClick}>
                    Search
                </Button>
                {error && <div style={{ color: 'red' }}>{error}</div>}
                <SubjectList subjects={filteredSubjects} />
            </Box>
        </div>
    );
};

export default Subjects;
