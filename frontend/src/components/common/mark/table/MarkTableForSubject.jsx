import React, {useCallback, useState} from "react";
import {Paper, Table, TableBody, TableContainer, TableHead, TablePagination, TableRow} from "@mui/material";
import TablePaginationActions from "../../../layouts/TablePaginationActions";
import MarkTableBodyRow from "./MarkTableBodyRow";
import TableCell from "@mui/material/TableCell";
import MarkTableHeadRow from "./MarkTableHeadRow";

const MarkTableForSubject = (
    {
        subjectId,
        students,
        onStudentDelete,
        onMarkCreate,
        onMarkUpdate,
        onMarkDelete
    }) => {

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const handleChangePage = useCallback((event, newPage) => {
        setPage(newPage);
    }, []);

    const handleChangeRowsPerPage = useCallback((event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    }, []);

    const cellStyles = {
        border: "1px solid #e0e0e0",
        widthNumber: "20px",
        widthStudent: "250px",
        widthEmail: "150px",
        widthActions: "10px"
    };

    return (
        <>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 500}} aria-label="custom pagination table">
                    <TableHead>
                        <MarkTableHeadRow
                            cellStyles={cellStyles}
                        />
                    </TableHead>
                    <TableBody>
                        {students.length ? (
                            students.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((studentData, index) => (
                                    <MarkTableBodyRow
                                        key={studentData.student.id}
                                        studentData={studentData}
                                        onMarkCreate={onMarkCreate}
                                        onMarkUpdate={onMarkUpdate}
                                        onMarkDelete={onMarkDelete}
                                        onStudentDelete={onStudentDelete}
                                        subjectId={subjectId}
                                        index={index + page * rowsPerPage}
                                        cellStyles={cellStyles}
                                    />
                                ))
                        ) : (
                            <TableRow style={{backgroundColor: "#f0f0f0"}}>
                                <TableCell colSpan={5} align="center">No students data available</TableCell>
                            </TableRow>
                        )}
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
