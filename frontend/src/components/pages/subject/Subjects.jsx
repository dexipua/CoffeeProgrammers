import React, {useEffect, useState} from 'react';
import ButtonAppBar from '../../layouts/ButtonAppBar';
import SubjectService from '../../../services/SubjectService';
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
                    <strong>Subject</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderBody = () => {
        return filteredSubjects
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((subject, index) => {
                const subjectNumber = page * rowsPerPage + index + 1;
                return (
                    <TableRow key={subject.id} style={{ backgroundColor: getRowColor(index) }}>
                        <TableCell
                            align="center"
                            style={{ border: "1px solid #e0e0e0", width: "20px" }}
                        >
                            {subjectNumber}
                        </TableCell>
                        <TableCell align="center" style={{ border: "1px solid #e0e0e0" }}>
                            <Link to={`/subjects/getById/${subject.id}`}>{subject.name}</Link>
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
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search Subject by Name"
                />
                <Button onClick={handleSearchClick}>
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
                    count={filteredSubjects.length}
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

export default Subjects;
