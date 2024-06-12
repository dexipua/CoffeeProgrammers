import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import '../../../assets/styles/StudentsList.css';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TablePagination from "@mui/material/TablePagination";
import TableHead from "@mui/material/TableHead";
import TableBody from "@mui/material/TableBody";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import {Link} from "react-router-dom";
import {TablePaginationActions} from "../../layouts/TablePaginationActions";

const StudentsList = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAllStudents = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await StudentService.getAll(token);
                setFilteredStudents(response);
            } catch (error) {
                console.error('Error fetching students data:', error);
                setError('Error fetching students data. Please try again.');
            }
        };

        fetchAllStudents().then();
    }, []);

    const handleSearch = async () => {
        const token = localStorage.getItem('jwtToken');
        setFilteredStudents(await StudentService.getByName(firstName, lastName, token))
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

    const renderHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "20px"}}>
                    <strong>№</strong>
                </TableCell>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "250px"}}>
                    <strong>Student</strong>
                </TableCell>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "250px"}}>
                    <strong>Student`s email</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderBody = () => {
        return filteredStudents
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((student, index) => {
                const studentNumber = page * rowsPerPage + index + 1;
                return (
                    <TableRow key={student.id} style={{ backgroundColor: getRowColor(index) }}>
                        <TableCell
                            align="center"
                            style={{ border: "1px solid #e0e0e0", width: "20px" }}
                        >
                            {studentNumber}
                        </TableCell>
                        <TableCell align="center" style={{ border: "1px solid #e0e0e0" }}>
                            <Link to={`/students/${student.id}`}>{student.firstName} {student.lastName}</Link>
                        </TableCell>
                        <TableCell align="center" style={{ border: "1px solid #e0e0e0" }}>
                            {student.email}
                        </TableCell>
                    </TableRow>
                );
            });
    };


    return (
        <div className="students-list">
            <ButtonAppBar/>
            <Box mt={9}>
                <input
                    type="text"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder="First Name"
                />
                <input
                    type="text"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder="Last Name"
                />
                <Button onClick={handleSearch} style={{ backgroundColor: '#007bff', color: '#ffffff', padding: '4px 6px', borderRadius: '4px', border: 'none', cursor: 'pointer' }}>
                    Search
                </Button>
                {error && <div style={{color: 'red'}}>{error}</div>}
                <TableContainer>
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
                    colSpan={2}
                    count={filteredStudents.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                    ActionsComponent={TablePaginationActions}
                />
            </Box>
        </div>
    );
};

export default StudentsList;
