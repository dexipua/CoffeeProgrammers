import React, {useEffect, useState} from 'react';
import SubjectService from '../../../../services/SubjectService';
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
    Typography
} from '@mui/material';
import SubjectSearchBar from "../../../layouts/SubjectSearchBar";
import {Link} from "react-router-dom";

const DeleteTeacherFromSubject = () => {
    const [subjectsWithTeacher, setSubjectsWithTeacher] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);
    const [selectedSubjectId, setSelectedSubjectId] = useState(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [searchName, setSearchName] = useState('');

    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchSubjects = async () => {

            try {
                const response = await SubjectService.getAll(token);
                const filteredResponse = response.filter(
                    (subject) => subject.teacher !== null
                );
                setSubjectsWithTeacher(filteredResponse);
                setFilteredSubjects(filteredResponse);
            } catch (error) {
                console.error('Error fetching subjects:', error);
            }
        };

        fetchSubjects();
    }, [token]);

    const handleDeleteTeacherFromSubject = async () => {
        try {
            await SubjectService.deleteTeacher(selectedSubjectId, token);

            const filteredSubjects = subjectsWithTeacher.filter(subject => subject.id !== selectedSubjectId);
            setSubjectsWithTeacher(filteredSubjects);
            setFilteredSubjects(filteredSubjects);
            setSelectedSubjectId('');
        } catch (error) {
            console.error('Error deleting subject:', error);
        }
    };

    const handleSearch = async () => {
        if (searchName) {
            const response = await SubjectService.getByNameContaining(searchName, token)
            const filteredResponse = response.filter(
                (subject) => subject.teacher !== null
            );
            setFilteredSubjects(filteredResponse);
            setSubjectsWithTeacher(filteredResponse);
            setSearchName('')
        } else {
            setFilteredSubjects(subjectsWithTeacher);
        }
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const getRowColor = (index) => {
        return index % 2 === 0 ? '#f0f0f0' : 'white';
    };

    const cellWidthStyle = {
        widthNumber: "20px",
        widthSubject: "250px",
        widthAction: "20px"};
    const cellBorderStyle = {border: "1px solid #e0e0e0"};

    const renderHead = () => {
        return (
            <TableRow>
                <TableCell align="center"
                           style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                    <strong>№</strong>
                </TableCell>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthSubject}}>
                    <strong>Subject</strong>
                </TableCell>
                <TableCell align="center" style={cellBorderStyle}>
                    <strong>Teacher</strong>
                </TableCell>
                <TableCell align="center"
                           style={{...cellBorderStyle, width: cellWidthStyle.widthAction}}>
                    <strong>Select</strong>
                </TableCell>
            </TableRow>
        )
    };

    const renderBody = () => {
        if (!filteredSubjects.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={4} align="center" style={cellBorderStyle}>No subject data available</TableCell>
                </TableRow>
            );
        }

        return (
            <>
                {filteredSubjects.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((subject, index) => (
                        <TableRow key={subject.id} style={{backgroundColor: getRowColor(index)}}>
                            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                                {page * rowsPerPage + index + 1}
                            </TableCell>
                            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthSubject}}>
                                <Link to={`/subjects/${subject.id}`} style={{color: 'inherit' }}>
                                    {subject.name}
                                </Link>
                            </TableCell>
                            <TableCell align="center" style={cellBorderStyle}>
                                {subject.teacher && (
                                    <Link to={`/teachers/${subject.teacher.id}`} style={{color: 'inherit' }}>
                                        {subject.teacher.firstName} {subject.teacher.lastName}
                                    </Link>
                                )}

                            </TableCell>
                            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthAction}}>
                                <Radio
                                    size="small"
                                    checked={selectedSubjectId === subject.id}
                                    onChange={() => setSelectedSubjectId(subject.id)}
                                    value={subject.id}
                                />
                            </TableCell>
                        </TableRow>
                    ))}
            </>
        );
    };


    return (
        <Box>
            <Typography variant="h5" gutterBottom>
                Delete Teacher from Subject
            </Typography>

            <Box
                width={800}
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"
                gap={2}
                p={2}
                sx={{
                    border: '1px solid #ddd',
                    borderRadius: '8px',
                    backgroundColor: '#ffffff',
                }}
            >
                <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    mb={2}
                    width="100%"
                    gap={2}
                >
                    <SubjectSearchBar
                        name={searchName}
                        onNameChange={(e) => setSearchName(e.target.value)}
                        onSearch={handleSearch}
                    />
                    <Button
                        onClick={handleDeleteTeacherFromSubject}
                        variant="contained"
                        disabled={!selectedSubjectId}
                        sx={{backgroundColor: '#d90000', color: '#ffffff', minWidth: '100px'}}
                    >
                        Delete
                    </Button>
                </Box>

                <TableContainer style={{width: '100%'}}>
                    <Table>
                        <TableHead>
                            {renderHead()}
                        </TableHead>
                        <TableBody>
                            {renderBody()}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10]}
                    component="div"
                    count={filteredSubjects.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Box>
        </Box>
    );
};

export default DeleteTeacherFromSubject;
