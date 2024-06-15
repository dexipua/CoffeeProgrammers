import React, {useEffect, useState} from 'react';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import {
    Box,
    CircularProgress,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import SubjectDateService from "../../../services/SubjectDateService";
import {Link, useNavigate} from "react-router-dom";

const StudentSchedule = () => {
    const [schedule, setSchedule] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem('jwtToken');
    const role = localStorage.getItem('role')
    const roleId = localStorage.getItem('roleId');

    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (role !== "STUDENT") {
                    navigate("*")
                }
                const response = await SubjectDateService.getAllByStudentId(roleId, token);
                console.log('Fetched schedule:', response);
                setSchedule(response);
            } catch (error) {
                console.error('Error fetching student schedule:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [roleId, token]);

    const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    const numOfLessons = ["FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH"];

    const generateTableData = () => {
        const tableData = {};

        schedule.forEach(item => {
            const day = item.dayOfWeek;
            const lesson = item.numOfLesson;
            const subject = item.subjectResponseSimple ? (
                <Link to={`/subjects/${item.subjectResponseSimple.id}`}>{item.subjectResponseSimple.name}</Link>
                ) : ""


            if (!tableData[lesson]) {
                tableData[lesson] = {};
            }

            tableData[lesson][day] = subject;
        });

        return tableData;
    };

    const tableData = generateTableData();

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
                {daysOfWeek.map(day => (
                    <TableCell key={day} align="center" style={{...cellBorderStyle, width: cellWidthStyle.width}}>
                        <strong>{day}</strong>
                    </TableCell>
                ))}
            </TableRow>
        );
    };

    const renderBody = () => {
        if (!numOfLessons.length || !daysOfWeek.length) {
            return (
                <TableRow style={{backgroundColor: "#f0f0f0"}}>
                    <TableCell colSpan={daysOfWeek.length + 1} align="center" style={cellBorderStyle}>
                        No schedule data available
                    </TableCell>
                </TableRow>
            );
        }

        return (
            numOfLessons.map((lesson, index) => (
                <TableRow key={lesson} style={{backgroundColor: getRowColor(index)}}>
                    <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.width}}>
                        {index + 1}
                    </TableCell>
                    {daysOfWeek.map(day => (
                        <TableCell key={`${lesson}-${day}`} align="center"
                                   style={{...cellBorderStyle, width: cellWidthStyle.width}}>
                            {tableData[lesson] && tableData[lesson][day] ? tableData[lesson][day] : ""}
                        </TableCell>
                    ))}
                </TableRow>
            ))
        );
    };

    return (
        <Box
            mt="80px"
            sx={{
                width: "1000px",
                margin: '80px auto 0', // центрування по горизонталі, зберігає mt="80px"
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
                padding: '20px',
            }}
        >
            <ApplicationBar/>
            <Box display="flex" flexDirection="column" alignItems="center">
                <Typography variant="h5" component="h3" gutterBottom style={{textAlign: 'center'}}>
                    Schedule
                </Typography>
                {loading ? (
                    <CircularProgress/>
                ) : (
                    <TableContainer component={Paper}>
                        <Table sx={{minWidth: 700}} aria-label="custom pagination table">
                            <TableHead>{renderHead()}</TableHead>
                            <TableBody>{renderBody()}</TableBody>
                        </Table>
                    </TableContainer>
                )}
            </Box>
        </Box>
    );
};

export default StudentSchedule;
