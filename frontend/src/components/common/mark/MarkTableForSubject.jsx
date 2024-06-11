import React, {useState} from "react";

import {
    Box,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from "@mui/material";

import SubjectService from "../../../services/SubjectService";
import DeleteButton from "../../layouts/DeleteButton";
import MarkMenu from "./MarkMenu";
import TablePaginationActions from "../../layouts/TablePaginationActions";
import CreateMarkButton from "./CreateMarkButton";

function MarkTableForSubject(
    {
        subjectId,
        students,
        onStudentDelete,
        onMarkCreate,
        onMarkUpdate,
        onMarkDelete
    }) {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };


    const tableCellStyle = {border: "1px solid #e0e0e0"};
    const cellWidthStyle = {width: "20px", widthName: "250px", widthEmail: "150px", widthActions: "10px"};

    function renderHead() {
        return (
            <TableRow>
                <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.width}}>
                    <strong>№</strong>
                </TableCell>
                <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthName}}>
                    <strong>Student</strong>
                </TableCell>
                <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthEmail}}>
                    <strong>Email</strong>
                </TableCell>
                <TableCell align="center" style={tableCellStyle}>
                    <strong>Marks</strong>
                </TableCell>
                <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthActions}}>
                    <strong></strong>
                </TableCell>
            </TableRow>
        );
    }

    function renderBody() {
        if (!students.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={5} align="center">No students data available</TableCell>
                </TableRow>
            );
        }

        return students
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((studentData, index) => {
                const student = studentData.student;
                const studentNumber = page * rowsPerPage + index + 1;
                const marks = studentData.marks.map((mark, index) => (
                    <React.Fragment key={index}>
                        <MarkMenu
                            onMarkUpdate={(newMark) => onMarkUpdate(student.id, newMark)}
                            onMarkDelete={() => onMarkDelete(student.id, mark.id)}
                            mark={mark}
                        />
                        {index !== studentData.marks.length - 1 && ", "}
                    </React.Fragment>
                ));
                return (
                    <TableRow key={student.id} style={{backgroundColor: index % 2 === 0 ? "#f0f0f0" : "white"}}>
                        <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.width}}>
                            {studentNumber}
                        </TableCell>
                        <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthName}}>
                            {student.lastName} {student.firstName}
                        </TableCell>
                        <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthEmail}}>
                            {student.email}
                        </TableCell>
                        <TableCell align="center" style={tableCellStyle}>
                            {marks}
                        </TableCell>
                        <TableCell align="center" style={{...tableCellStyle, width: cellWidthStyle.widthActions}}>
                            <Box sx={{display: 'flex', gap: 1}}>
                                <CreateMarkButton
                                    subjectId={subjectId}
                                    studentId={student.id}
                                    onMarkCreate={onMarkCreate}
                                />
                                <DeleteButton
                                    text={"Do you really want to delete this student?"}
                                    deleteFunction={async () => {
                                        await SubjectService.deleteStudent(
                                            subjectId,
                                            student.id,
                                            localStorage.getItem('jwtToken')
                                        )
                                        onStudentDelete(student.id);
                                    }}
                                />
                            </Box>
                        </TableCell>
                    </TableRow>
                );
            });
    }

    return (
        <>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 500}} aria-label="custom pagination table">
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
                colSpan={5}
                count={students.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                ActionsComponent={TablePaginationActions}
            />
        </>
    );
}

export default MarkTableForSubject;
