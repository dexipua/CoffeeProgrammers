import React from "react";
import {TableCell, TableRow} from "@mui/material";

const MarkTableHeadRow = ({cellStyles}) => {

    return (
        <TableRow>
            <TableCell align="center" style={{ ...cellStyles, width: cellStyles.widthNumber }}>
                <strong>№</strong>
            </TableCell>
            <TableCell align="center" style={{ ...cellStyles, width: cellStyles.widthStudent }}>
                <strong>Student</strong>
            </TableCell>
            <TableCell align="center" style={{ ...cellStyles, width: cellStyles.widthEmail }}>
                <strong>Email</strong>
            </TableCell>
            <TableCell align="center" style={{ ...cellStyles }}>
                <strong>Marks</strong>
            </TableCell>
            <TableCell align="center" style={{ ...cellStyles, width: cellStyles.widthActions }}>
                <strong></strong>
            </TableCell>
        </TableRow>
    );
};

export default MarkTableHeadRow;
