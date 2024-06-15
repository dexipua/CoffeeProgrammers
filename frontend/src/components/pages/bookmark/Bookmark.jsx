import React, {useEffect, useState} from 'react';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from '@mui/material';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import MarkService from "../../../services/MarkService";

const Bookmark = () => {
    const [marks, setMarks] = useState([]);
    const role = localStorage.getItem('role')

    const navigate = useNavigate();

    useEffect(() => {
        const fetchMarks = async () => {
            try {
                if (role !== "STUDENT") {
                    navigate("*")
                }
                const token = localStorage.getItem('jwtToken');
                const response = await MarkService.getBookmark(token);
                console.log(response);
                setMarks(response);
            } catch (error) {
                console.error('Error fetching bookmarks:', error);
            }
        };

        fetchMarks();
    }, []);

    const cellWidthStyle = { widthNumber: "10px", widthSubject: "200px"};
    const cellBorderStyle = { border: "1px solid #e0e0e0" };

    const getRowColor = (index) => {
        return index % 2 === 0 ? '#f0f0f0' : 'white';
    };

    const renderHead = () => (
        <TableRow>
            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}><strong>№</strong></TableCell>
            <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthSubject}}><strong>Subject</strong></TableCell>
            <TableCell align="center" style={cellBorderStyle}><strong>Marks</strong></TableCell>
        </TableRow>
    );

    const renderBody = () => {
        if (!marks.length) {
            return (
                <TableRow>
                    <TableCell colSpan={3} align="center" style={cellBorderStyle}>No bookmarks available</TableCell>
                </TableRow>
            );
        }

        return marks.map((bookmark, index) => (
            <TableRow key={index} style={{ backgroundColor: getRowColor(index) }}>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>{index + 1}</TableCell>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthSubject}}>{bookmark.subjectResponseSimple.name}</TableCell>
                <TableCell align="center" style={cellBorderStyle}>{bookmark.marks.map(mark => mark.value).join(', ')}</TableCell>
            </TableRow>
        ));
    };

    return (
        <Box
            mt="80px"
            sx={{
                width: "1000px",
                margin: '80px auto 0',
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#FFFFFF',
                padding: '20px',
            }}
        >
            <ApplicationBar />
            <Typography variant="h5" component="h3" gutterBottom style={{ textAlign: 'center' }}>Bookmark</Typography>
            <TableContainer component={Paper} style={{ width: '100%', maxWidth: 'calc(100% - 40px)', margin: 'auto' }}>
                <Table sx={{ minWidth: 700 }} aria-label="bookmark table">
                    <TableHead>{renderHead()}</TableHead>
                    <TableBody>{renderBody()}</TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default Bookmark;
