import React from "react";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow} from "@mui/material";
import TablePaginationActions from "../../layouts/TablePaginationActions";
import {Link} from "react-router-dom";
import Box from "@mui/material/Box";

function StudentTable({students}) {
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

    const cellWidthStyle = {width: "10px", widthName: "250px", widthEmail: "150px"};
    const cellBorderStyle = {border: "1px solid #e0e0e0"};

    const renderHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.width}}>
                    <strong>№</strong>
                </TableCell>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthName}}>
                    <strong>Student</strong>
                </TableCell>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthEmail}}>
                    <strong>Email</strong>
                </TableCell>
            </TableRow>
        );
    };

    const renderBody = () => {
        if (!students.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={3} align="center" style={cellBorderStyle}>No students data available</TableCell>
                </TableRow>
            );
        }

        return students
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((student, index) => {
                const studentNumber = page * rowsPerPage + index + 1;
                return (
                    <TableRow key={student.id} style={{backgroundColor: getRowColor(index)}}>
                        <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.width}}>
                            {studentNumber}
                        </TableCell>
                        <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthName}}>
                            <Link
                                to={`/students/${student.id}`}
                                style={{color: 'inherit'}}
                            >
                                {student.firstName} {student.lastName}
                            </Link>
                        </TableCell>
                        <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthEmail}}>
                            {student.email}
                        </TableCell>
                    </TableRow>
                );
            });
    };

    return (
        <Box width={"100%"} style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <TableContainer component={Paper}>
                <Table aria-label="custom pagination table">
                    <TableHead>{renderHead()}</TableHead>
                    <TableBody>{renderBody()}</TableBody>
                </Table>
            </TableContainer>
            <div style={{alignSelf: 'center'}}>
                <TablePagination
                    rowsPerPageOptions={[10]}
                    colSpan={3}
                    count={students.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                    ActionsComponent={TablePaginationActions}
                />
            </div>
        </Box>
    );
}

export default StudentTable;
