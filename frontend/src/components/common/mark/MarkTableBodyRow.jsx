import React from "react";
import {Box, TableCell, TableRow} from "@mui/material";
import SubjectService from "../../../services/SubjectService";
import DeleteButton from "../../layouts/DeleteButton";
import MarkMenu from "./MarkMenu";
import CreateMarkButton from "./CreateMarkButton";

const MarkTableBodyRow = (
    {
        studentData,
        onMarkCreate,
        onMarkUpdate,
        onMarkDelete,
        onStudentDelete,
        subjectId,
        index,
        cellStyles
    }) => {

    const {student, marks} = studentData;
    const studentNumber = index + 1;

    return (
        <TableRow key={student.id} style={{backgroundColor: index % 2 === 0 ? "#f0f0f0" : "white"}}>
            <TableCell align="center" style={{...cellStyles, width: cellStyles.widthNumber}}>
                {studentNumber}
            </TableCell>
            <TableCell align="center" style={{...cellStyles, width: cellStyles.widthStudent}}>
                {`${student.lastName} ${student.firstName}`}
            </TableCell>
            <TableCell align="center" style={{...cellStyles, width: cellStyles.widthEmail}}>
                {student.email}
            </TableCell>
            <TableCell align="center" style={{...cellStyles}}>
                {marks.map((mark, index) => (
                    <React.Fragment key={index}>
                        <MarkMenu
                            onMarkUpdate={(newMark) => onMarkUpdate(student.id, newMark)}
                            onMarkDelete={() => onMarkDelete(student.id, mark.id)}
                            mark={mark}
                        />
                        {index !== marks.length - 1 && ", "}
                    </React.Fragment>
                ))}
            </TableCell>
            <TableCell align="center" style={{...cellStyles, width: cellStyles.widthActions}}>
                <Box sx={{display: 'flex', gap: 1}}>
                    <CreateMarkButton
                        subjectId={subjectId}
                        studentId={student.id}
                        onMarkCreate={onMarkCreate}
                    />
                    <DeleteButton
                        text={"Do you really want to delete this student?"}
                        deleteFunction={() => {
                            SubjectService.deleteStudent(
                                subjectId,
                                student.id,
                                localStorage.getItem('jwtToken'))
                                .then(
                                    () => onStudentDelete(student.id)
                                );
                        }}
                    />
                </Box>
            </TableCell>
        </TableRow>
    );
};

export default MarkTableBodyRow;
