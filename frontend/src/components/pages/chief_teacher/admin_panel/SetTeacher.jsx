import React, {useEffect, useState} from 'react';
import SubjectService from '../../../../services/SubjectService';
import TeacherService from '../../../../services/TeacherService';
import {
    Box,
    Button,
    Radio,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow,
    Typography,
} from '@mui/material';
import UserSearchBar from "../../../layouts/UserSearchBar";
import SubjectSearchBar from "../../../layouts/SubjectSearchBar";
import {Link} from "react-router-dom";
import ErrorSnackbar from "../../../layouts/ErrorSnackbar";

const SetTeacher = () => {
    const [subjectsWithoutTeachers, setSubjectsWithoutTeachers] = useState([]);
    const [teachers, setTeachers] = useState([]);
    const [selectedSubjectId, setSelectedSubjectId] = useState('');
    const [selectedTeacherId, setSelectedTeacherId] = useState('');

    const [searchFirstname, setSearchFirstname] = useState('');
    const [searchLastname, setSearchLastname] = useState('');
    const [searchSubjectName, setSearchSubjectName] = useState('');

    const [filteredTeachers, setFilteredTeachers] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);

    const [pageTeachers, setPageTeachers] = useState(0);
    const [pageSubjects, setPageSubjects] = useState(0);

    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [showSnackbar, setShowSnackbar] = useState(false);
    const [error, setError] = useState(null);


    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchSubjectsWithoutTeachers = async () => {
            try {
                const subjectsResponse = await SubjectService.getAll(token);
                const teachersResponse = await TeacherService.getAll(token);
                const subjectsWithoutTeachers = subjectsResponse.filter(
                    (subject) => subject.teacher === null
                );
                setSubjectsWithoutTeachers(subjectsWithoutTeachers);
                setFilteredSubjects(subjectsWithoutTeachers)

                setTeachers(teachersResponse);
                setFilteredTeachers(teachersResponse);
            } catch (error) {
                console.log("Error")
            }
        };

        fetchSubjectsWithoutTeachers();
    }, [token]);

    const handleSetTeacher = async () => {
        if (!selectedSubjectId || !selectedTeacherId) return;
        try {
            await SubjectService.setTeacher(selectedSubjectId, selectedTeacherId, token);
            const updatedSubjectsWithoutTeachers = subjectsWithoutTeachers.filter(
                (subject) => subject.id !== selectedSubjectId
            );
            setSubjectsWithoutTeachers(updatedSubjectsWithoutTeachers);
            setFilteredSubjects(updatedSubjectsWithoutTeachers);
            setSelectedSubjectId('');
            setSelectedTeacherId('');
        } catch (error) {
            setError(error.response.data.messages);
            setShowSnackbar(true);
        }
    };

    const handleSearchTeacher = async () => {
        if (searchFirstname || searchLastname) {
            const response = await TeacherService.getByName(searchFirstname, searchLastname, token)
            setFilteredTeachers(response);
            setSearchFirstname('');
            setSearchLastname('');
        } else {
            setFilteredTeachers(teachers);
        }
    };

    const handleSearchSubject = async () => {
        if (searchSubjectName) {
            const response = await SubjectService.getByNameContaining(searchSubjectName, token)
            const filteredResponse = response.filter(
                (subject) => subject.teacher === null
            );
            setFilteredSubjects(filteredResponse);
            setSearchSubjectName('')
        } else {
            setFilteredSubjects(subjectsWithoutTeachers);
        }
    };


    const cellBorderStyle = {border: "1px solid #e0e0e0"};

    const handleChangePageTeachers = (event, newPage) => {
        setPageTeachers(newPage);
    };

    const handleChangeRowsPerPageTeachers = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPageTeachers(0);
    };

    const handleChangePageSubjects = (event, newPage) => {
        setPageSubjects(newPage);
    };

    const handleChangeRowsPerPageSubjects = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPageSubjects(0);
    };

    const getRowColor = (index) => {
        return index % 2 === 0 ? '#f0f0f0' : 'white';
    };

    const renderSubjectHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{...cellBorderStyle}}>
                    <strong>Select Subject</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderSubjectBody = () => {
        if (!filteredSubjects.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={1} align="center" style={cellBorderStyle}>No subject data available</TableCell>
                </TableRow>
            );
        }
        return (
            <TableBody>
                {filteredSubjects
                    .slice(pageSubjects * rowsPerPage, pageSubjects * rowsPerPage + rowsPerPage)
                    .map((subject, index) => (
                        <TableRow key={subject.id} style={{backgroundColor: getRowColor(index)}}>
                            <TableCell style={{...cellBorderStyle}}>
                                <Radio
                                    size="small"
                                    checked={selectedSubjectId === subject.id}
                                    onChange={() => setSelectedSubjectId(subject.id)}
                                />
                                {subject.name}
                            </TableCell>
                        </TableRow>
                    ))}
            </TableBody>
        );
    };

    const renderTeacherHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{...cellBorderStyle}}>
                    <strong>Select Teacher</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderTeacherBody = () => {
        if (!filteredTeachers.length) {
            return (
                <TableRow style={{ backgroundColor: "#f0f0f0" }}>
                    <TableCell colSpan={4} align="center" style={cellBorderStyle}>No teachers data available</TableCell>
                </TableRow>
            );
        }
        return (
            <TableBody>
                {filteredTeachers
                    .slice(pageTeachers * rowsPerPage, pageTeachers * rowsPerPage + rowsPerPage)
                    .map((teacher, index) => (
                        <TableRow key={teacher.id} style={{backgroundColor: getRowColor(index)}}>
                            <TableCell style={{...cellBorderStyle}}>
                                <Radio
                                    size="small"
                                    checked={selectedTeacherId === teacher.id}
                                    onChange={() => setSelectedTeacherId(teacher.id)}
                                />
                                <Link to={`/teachers/${teacher.id}`} style={{color: 'inherit' }}>
                                    {teacher.firstName} {teacher.lastName}
                                </Link>
                            </TableCell>
                        </TableRow>
                    ))}
            </TableBody>
        );
    };

    return (
        <Box p={3}>
            <Typography variant="h5" gutterBottom>
                Set Teacher to Subject
            </Typography>


                    <Button
                        variant="contained"
                        disabled={!selectedSubjectId || !selectedTeacherId}
                        onClick={handleSetTeacher}
                    >
                        Add Teacher
                    </Button>

            <Box mt={3} display="flex" justifyContent="space-between">
                {/* Subjects */}
                <Box
                    height="auto"
                    my={2}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    alignItems="center"
                    gap={2}
                    p={2}
                    sx={{
                        flex: 1,
                        mr: 2,
                        p: 2, display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        border: '1px solid #ddd',
                        borderRadius: '8px',
                        backgroundColor: '#ffffff',
                    }}
                >
                    <Box mb={2}>
                        <SubjectSearchBar
                            name={searchSubjectName}
                            onNameChange={(e) => setSearchSubjectName(e.target.value)}
                            onSearch={handleSearchSubject}
                        />
                    </Box>
                    <TableContainer sx={{minWidth: 450, flexGrow: 1}}>
                        <Table>
                            <TableHead>
                                {renderSubjectHead()}
                            </TableHead>
                            {renderSubjectBody()}
                        </Table>
                    </TableContainer>

                    <TablePagination
                        rowsPerPageOptions={[10]}
                        component="div"
                        count={filteredSubjects.length}
                        rowsPerPage={rowsPerPage}
                        page={pageSubjects}
                        onPageChange={handleChangePageSubjects}
                        onRowsPerPageChange={handleChangeRowsPerPageSubjects}
                    />
                </Box>

                {/* Teachers */}
                <Box
                    height="auto"
                    my={2}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    alignItems="center"
                    gap={2}
                    p={2}
                    sx={{
                        flex: 1,
                        mr: 2,
                        p: 2, display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        border: '1px solid #ddd',
                        borderRadius: '8px',
                        backgroundColor: '#ffffff',
                    }}
                >
                    <Box mb={2}>
                        <UserSearchBar
                            firstName={searchFirstname}
                            lastName={searchLastname}
                            onFirstNameChange={(e) => setSearchFirstname(e.target.value)}
                            onLastNameChange={(e) => setSearchLastname(e.target.value)}
                            onSearch={handleSearchTeacher}
                        />
                    </Box>

                    <TableContainer sx={{minWidth: 450}}>
                        <Table>
                            <TableHead>
                                {renderTeacherHead()}
                            </TableHead>
                            {renderTeacherBody()}
                        </Table>
                    </TableContainer>

                    <TablePagination
                        rowsPerPageOptions={[10]}
                        component="div"
                        count={filteredTeachers.length}
                        rowsPerPage={rowsPerPage}
                        page={pageTeachers}
                        onPageChange={handleChangePageTeachers}
                        onRowsPerPageChange={handleChangeRowsPerPageTeachers}
                    />
                </Box>
            </Box>
             <ErrorSnackbar
                  open={showSnackbar}
                  onClose={() => setShowSnackbar(false)}
                  message={error}
             />
        </Box>
    );
};

export default SetTeacher;
