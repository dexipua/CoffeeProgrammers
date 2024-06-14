import React, {useEffect, useState} from 'react';
import StudentService from '../../../services/StudentService';
import Box from '@mui/material/Box';
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TablePagination from "@mui/material/TablePagination";
import TableBody from "@mui/material/TableBody";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Radio from '@mui/material/Radio';
import UserSearchBar from "../../layouts/UserSearchBar";
import Button from "@mui/material/Button";
import SubjectService from "../../../services/SubjectService";
import TablePaginationActions from "../../layouts/TablePaginationActions";

const AddStudentList = ({subjectId, onStudentAdd}) => {

    const [students, setStudents] = useState([]);
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [firstName, setFirstName] = useState('');

    const [lastName, setLastName] = useState('');
    const [selectedStudentId, setSelectedStudentId] = useState(null);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAllStudents = async () => {
            try {
                const token = localStorage.getItem('jwtToken');
                const response = await StudentService.findAllBySubjectsIdIsNot(subjectId, token);
                setStudents(response);
                setFilteredStudents(response);
            } catch (error) {
                console.error('Error fetching students data:', error);
                setError('Error fetching students data. Please try again.');
            }
        };

        fetchAllStudents();
    }, [subjectId]);

    const handleSearch = async () => {
        if (firstName || lastName) {
            const token = localStorage.getItem('jwtToken');
            const response = await StudentService.getByName(firstName, lastName, token);
            setFilteredStudents(response);
            setSelectedStudentId("")
            setFirstName("")
            setLastName("")
        } else {
            setFilteredStudents(students);
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

    const handleRadioChange = (studentId) => {
        setSelectedStudentId(studentId);
    };

    const handleAddStudent = async () => {
        await SubjectService.addStudent(
            subjectId,
            selectedStudentId,
            localStorage.getItem('jwtToken')
        );
        const temp = students.filter(
            (student) => student.id !== selectedStudentId
        )
        setFilteredStudents(temp)
        setStudents(temp)
        onStudentAdd(selectedStudentId)
    }
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
                <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "60px"}}>
                    <strong>Select</strong>
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
                    <TableRow key={student.id} style={{backgroundColor: getRowColor(index)}}>
                        <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "20px"}}>
                            {studentNumber}
                        </TableCell>
                        <TableCell align="center" style={{border: "1px solid #e0e0e0"}}>
                            {student.firstName} {student.lastName}
                        </TableCell>
                        <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "150px"}}>
                            {student.email}
                        </TableCell>
                        <TableCell align="center" style={{border: "1px solid #e0e0e0", width: "60px"}}>
                            <Radio
                                size="small"
                                checked={selectedStudentId === student.id}
                                onChange={() => handleRadioChange(student.id)}
                                value={student.id}
                                name="student-radio-button"
                            />
                        </TableCell>
                    </TableRow>
                );
            });
    };

    return (
        <Box
            width={600}
            height="auto"
            my={2}
            display="flex"
            flexDirection="column"
            justifyContent="flex-start"
            alignItems="flex-start"
            gap={1}
            p={2}
            sx={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
            }}
        >
            <div style={{display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '8px'}}>
                <UserSearchBar
                    firstName={firstName}
                    lastName={lastName}
                    onFirstNameChange={(e) => setFirstName(e.target.value)}
                    onLastNameChange={(e) => setLastName(e.target.value)}
                    onSearch={handleSearch}
                />
                <Button
                    onClick={handleAddStudent}
                    variant="contained"
                    disabled={selectedStudentId === null}
                >
                    Submit
                </Button>
            </div>
            {error && <div style={{color: 'red'}}>{error}</div>}

            <TableContainer>
                <Table>
                    <TableBody>
                        {renderHead()}
                    </TableBody>
                </Table>
                <Table>
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
    );
};

export default AddStudentList;
