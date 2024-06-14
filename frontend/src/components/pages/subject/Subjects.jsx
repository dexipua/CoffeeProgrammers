import React, {useEffect, useState} from 'react';
import ApplicationBar from '../../layouts/app_bar/ApplicationBar';
import SubjectService from '../../../services/SubjectService';
import Box from '@mui/material/Box';
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableBody from "@mui/material/TableBody";
import TablePagination from "@mui/material/TablePagination";
import TablePaginationActions from "../../layouts/TablePaginationActions";
import SubjectSearchBar from "../../layouts/SubjectSearchBar";
import {Paper} from "@mui/material";
import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";

const Subjects = () => {
    const [searchSubject, setSearchSubject] = useState('');
    const [subjects, setSubjects] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);
    const [error, setError] = useState(null);

    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchAllSubjects = async () => {
            try {
                const response = await SubjectService.getAll(token);
                setSubjects(response);
                setFilteredSubjects(response);
            } catch (error) {
                console.error('Error fetching subjects data:', error);
                setError('Error fetching subjects data. Please try again.');
            }
        };

        fetchAllSubjects();
    }, []);

    const handleSearch = async () => {
        if (searchSubject) {
            const response = await SubjectService.getByNameContaining(searchSubject, token);
            setFilteredSubjects(response);
        } else {
            setFilteredSubjects(subjects);
        }
    };

    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

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
                            <TableCell  align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                                {page * rowsPerPage + index + 1}
                            </TableCell>
                            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthSubject}}>
                                <Link to={`${subject.id}`} style={{color: 'inherit' }}>
                                    {subject.name}
                                </Link>
                            </TableCell>
                            <TableCell align="center" style={cellBorderStyle}>
                                {subject.teacher && (
                                    <Link to={`/teachers/${subject.teacher.id}`} style={{color: 'inherit' }}>
                                        { subject.teacher.lastName + " " + subject.teacher.firstName}
                                    </Link>
                                )}

                            </TableCell>
                        </TableRow>
                    ))}
            </>
        );
    };


    return (
        <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            p={2}
        >

            <Typography mt="80px" variant="h5" gutterBottom>
                Subjects
            </Typography>
            <Box
                width="80%"
                maxWidth="800px"
                display="flex"
                flexDirection="column"
                alignItems="center"
                gap={2}
                p={2}
                sx={{
                    border: '1px solid #ddd',
                    borderRadius: '8px',
                    backgroundColor: '#ffffff',
                }}
            >
                <ApplicationBar />
                <Box display="flex" flexDirection="column" alignItems="center" width="100%" gap={2}>
                    <Box display="flex" justifyContent="center" alignItems="center" width="100%">
                        <SubjectSearchBar
                            name={searchSubject}
                            onNameChange={(e) => setSearchSubject(e.target.value)}
                            onSearch={handleSearch}
                            fullWidth
                        />
                    </Box>
                    <TableContainer component={Paper} sx={{width: '100%'}}>
                        <Table sx={{minWidth: 700}} aria-label="custom pagination table">
                            <TableHead>{renderHead()}</TableHead>
                            <TableBody>{renderBody()}</TableBody>
                        </Table>
                    </TableContainer>
                    <div style={{alignSelf: 'center'}}>
                        <TablePagination
                            rowsPerPageOptions={[10]}
                            colSpan={3}
                            count={subjects.length}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            onPageChange={handleChangePage}
                            onRowsPerPageChange={handleChangeRowsPerPage}
                            ActionsComponent={TablePaginationActions}
                        />
                    </div>
                </Box>
                {error && <Box color="red">{error}</Box>}
            </Box>
        </Box>
    );
};
export default Subjects;
