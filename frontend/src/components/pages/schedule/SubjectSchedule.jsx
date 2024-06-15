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
import ScheduleDeleteButton from "../../common/schedule/ScheduleDeleteButton";
import ScheduleCreateButton from "../../common/schedule/ScheduleCreateButton";

const SubjectSchedule = ({subjectId}) => {
    const [tableData, setTableData] = useState({});
    const [loading, setLoading] = useState(true);

    const token = localStorage.getItem('jwtToken');
    const roleId = localStorage.getItem('roleId');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await SubjectDateService.getAllBySubjectId(subjectId, token);
                console.log('Fetched schedule:', response);
                generateTableData(response);
            } catch (error) {
                console.error('Error fetching student schedule:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [subjectId, roleId, token]);

    const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    const numOfLessons = ["FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH"];

    const handleCreate = async (dayOfWeek, numOfLesson) => {
        try {
            const response = await SubjectDateService.create(
                {
                    dayOfWeek: dayOfWeek,
                    numOfLesson: numOfLesson
                },
                subjectId,
                token
            );

            // Update tableData with the newly created subject
            setTableData(prevTableData => {
                const newLessonData = {
                    ...prevTableData[numOfLesson],
                    [dayOfWeek]: (
                        <ScheduleDeleteButton
                            key={response.id}
                            onDelete={() => handleDelete(response.id, dayOfWeek, numOfLesson)}
                        />
                    )
                };

                return {
                    ...prevTableData,
                    [numOfLesson]: newLessonData
                };
            });
        } catch (error) {
            console.error('Error creating subject date:', error);
        }
    }

    const handleDelete = async (subjectDateId, dayOfWeek, numOfLesson) => {
        try {
            await SubjectDateService.delete(subjectDateId, token);

            setTableData(prevTableData => {
                const newLessonData = {
                    ...prevTableData[numOfLesson],
                    [dayOfWeek]: (
                        <ScheduleCreateButton
                            onCreate={() => handleCreate(dayOfWeek, numOfLesson)}
                        />
                    )
                };

                return {
                    ...prevTableData,
                    [numOfLesson]: newLessonData
                };
            });
        } catch (error) {
            console.error('Error deleting subject date:', error);
        }
    }

    const generateTableData = (scheduleData) => {
        const newTableData = {};

        scheduleData.forEach(item => {
            const day = item.dayOfWeek;
            const lesson = item.numOfLesson;
            const subject = item.id ? (
                <ScheduleDeleteButton
                    onDelete={() => handleDelete(item.id, item.dayOfWeek, item.numOfLesson)}
                />
            ) : (
                <ScheduleCreateButton
                    onCreate={() => handleCreate(item.dayOfWeek, item.numOfLesson)}
                />
            )

            if (!newTableData[lesson]) {
                newTableData[lesson] = {};
            }

            newTableData[lesson][day] = subject;
        });

        setTableData(newTableData);
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
                            {tableData[lesson] && tableData[lesson][day] ?
                                tableData[lesson][day] : ""}
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

export default SubjectSchedule;
