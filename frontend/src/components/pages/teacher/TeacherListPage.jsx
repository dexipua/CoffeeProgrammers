import React, {useEffect, useState} from 'react';
import '../../../assets/styles/StudentsList.css';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import TeacherService from '../../../services/TeacherService';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import {Link} from "react-router-dom";
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableBody from "@mui/material/TableBody";
import TablePagination from "@mui/material/TablePagination";
import {TablePaginationActions} from "../../layouts/TablePaginationActions";

const TeacherListPage = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [filteredTeachers, setFilteredTeachers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAllTeachers = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await TeacherService.getAll(token);
                setFilteredTeachers(response);
            } catch (error) {
                console.error('Error fetching teachers data:', error);
                setError('Error fetching teachers data. Please try again.');
            }
        };

        fetchAllTeachers();
    }, []);

    const handleSearch = async () => {
        const token = localStorage.getItem('jwtToken');
        setFilteredTeachers(await TeacherService.getByName(firstName, lastName, token))
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
                    <strong>Teacher</strong>
                </TableCell>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "250px"}}>
                    <strong>Teacher`s email</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderBody = () => {
        return filteredTeachers
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((teacher, index) => {
                const teacherNumber = page * rowsPerPage + index + 1;
                return (
                    <TableRow key={teacher.id} style={{ backgroundColor: getRowColor(index) }}>
                        <TableCell
                            align="center"
                            style={{ border: "1px solid #e0e0e0", width: "20px" }}
                        >
                            {teacherNumber}
                        </TableCell>
                        <TableCell align="center" style={{ border: "1px solid #e0e0e0" }}>
                            <Link to={`/teachers/${teacher.id}`}>{teacher.firstName} {teacher.lastName}</Link>
                        </TableCell>
                        <TableCell align="center" style={{ border: "1px solid #e0e0e0" }}>
                            {teacher.email}
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
                <Button onClick={handleSearch}>
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
                    count={filteredTeachers.length}
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

export default TeacherListPage;
