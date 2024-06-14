import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import TeacherService from '../../../services/TeacherService';
import FormControlLabel from "@mui/material/FormControlLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import Box from "@mui/material/Box";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TablePagination from "@mui/material/TablePagination";
import TablePaginationActions from "../../layouts/TablePaginationActions";
import UserSearchBar from "../../layouts/UserSearchBar";
import Typography from "@mui/material/Typography";
import {Paper} from "@mui/material";
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import {Link} from "react-router-dom";

const Users = () => {
    const [role, setRole] = useState('STUDENT');
    const [users, setUsers] = useState([]);

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [filteredUsers, setFilteredUsers] = useState([]);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = role === 'STUDENT' ? (
                    await StudentService.getAll(token)
                ) : (
                    await TeacherService.getAll(token)
                )
                setUsers(response);
                setFilteredUsers(response)
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        fetchUsers();
    }, [role, token]);


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


    const cellWidthStyle = {widthNumber: "20px", widthEmail: "150px", widthAction: "20px"};
    const cellBorderStyle = {border: "1px solid #e0e0e0"};

    const renderHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "20px"}}>
                    <strong>№</strong>
                </TableCell>
                <TableCell align="center" style={{border: "1px solid #e0e0e0"}}>
                    <strong>Student</strong>
                </TableCell>
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "150px"}}>
                    <strong>Email</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderBody = () => {
        if (!filteredUsers.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={4} align="center" style={cellBorderStyle}>No users data available</TableCell>
                </TableRow>
            );
        }

        return filteredUsers
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((user, index) => {
                const studentNumber = page * rowsPerPage + index + 1;
                return (
                    <TableRow key={user.id} style={{backgroundColor: getRowColor(index)}}>
                        <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                            {studentNumber}
                        </TableCell>
                        <TableCell align="center" style={cellBorderStyle}>
                            <Link to={
                                role === "STUDENT" ? `/students/${user.id}` : `/teachers/${user.id}`}
                                  style={{color: 'inherit' }}
                            >
                                {user.firstName} {user.lastName}
                            </Link>


                        </TableCell>
                        <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthEmail}}>
                            {user.email}
                        </TableCell>
                    </TableRow>
                );
            });
    }

    const handleSearch = async () => {
        if (firstName || lastName) {
            const response = role === "STUDENT" ? (
                await StudentService.getByName(firstName, lastName, token)
            ) : (
                await TeacherService.getByName(firstName, lastName, token)
            )
            setFilteredUsers(response);
            setFirstName('');
            setLastName('')
        } else {
            setFilteredUsers(users);
        }
    };

    return (
        <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            p={2}
        >
            <ApplicationBar />
            <Typography mt="80px" variant="h5" gutterBottom>
                Users
            </Typography>
            <RadioGroup
                row
                aria-label="role"
                name="role"
                value={role}
                onChange={(e) => setRole(e.target.value)}
                sx={{ display: 'flex', justifyContent: 'center', width: '100%', mb: 2 }}
            >
                <FormControlLabel value="STUDENT" control={<Radio />} label="Student" />
                <FormControlLabel value="TEACHER" control={<Radio />} label="Teacher" />
            </RadioGroup>
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
                <Box
                    width="100%"
                    my={2}
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    gap={2}
                >
                    <Box display="flex" justifyContent="center" alignItems="center" mb={2} width="100%" gap={2}>
                        <UserSearchBar
                            firstName={firstName}
                            lastName={lastName}
                            onFirstNameChange={(e) => setFirstName(e.target.value)}
                            onLastNameChange={(e) => setLastName(e.target.value)}
                            onSearch={handleSearch}
                            fullWidth
                        />
                    </Box>
                    <TableContainer component={Paper} sx={{width: '100%'}}>
                        <Table>
                            <TableBody>
                                {renderHead()}
                                {renderBody()}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <div style={{alignSelf: 'center'}}>
                        <TablePagination
                            rowsPerPageOptions={[10]}
                            colSpan={3}
                            count={users.length}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            onPageChange={handleChangePage}
                            onRowsPerPageChange={handleChangeRowsPerPage}
                            ActionsComponent={TablePaginationActions}
                        />
                    </div>
                </Box>
            </Box>
        </Box>
    );
};

export default Users;
