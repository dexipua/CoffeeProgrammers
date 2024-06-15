import React, {useEffect, useState} from 'react';
import {Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from '@mui/material';
import SubjectDateService from "../../../services/SubjectDateService";
import ScheduleDeleteButton from "../../common/schedule/ScheduleDeleteButton";
import ScheduleCreateButton from "../../common/schedule/ScheduleCreateButton";
import Loading from "../../layouts/Loading";
import EventAvailableIcon from "@mui/icons-material/EventAvailable";
import ErrorSnackbar from "../../layouts/ErrorSnackbar";

const SubjectSchedule = ({subjectId, isTeacherOfThisSubject}) => {
    const [tableData, setTableData] = useState({});
    const [loading, setLoading] = useState(true);

    const [errorMessage, setErrorMessage] = useState("");
    const [showSnackbar, setShowSnackbar] = useState(false);

    const token = localStorage.getItem('jwtToken');
    const roleId = localStorage.getItem('roleId');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await SubjectDateService.getAllBySubjectId(subjectId, token);
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
            setErrorMessage(error.response.data.messages);
            setShowSnackbar(true);
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
            const subjectView = !isTeacherOfThisSubject ? (
                item.id && (
                    <EventAvailableIcon sx={{
                        color: "#5B5B5BEF",
                    }}/>
                )
            ) : (item.id ? (
                <ScheduleDeleteButton
                    onDelete={() => handleDelete(item.id, item.dayOfWeek, item.numOfLesson)}
                />
            ) : (
                <ScheduleCreateButton
                    onCreate={() => handleCreate(item.dayOfWeek, item.numOfLesson)}
                />
            ))


            if (!newTableData[lesson]) {
                newTableData[lesson] = {};
            }

            newTableData[lesson][day] = subjectView;
        });

        setTableData(newTableData);
    };

    const getRowColor = (index) => {
        return index % 2 === 0 ? '#f0f0f0' : 'white';
    };

    const cellWidthStyle = {widthNumber: "30px", widthDays: "100px"};
    const cellBorderStyle = {border: "1px solid #e0e0e0"};

    const renderHead = () => {
        return (
            <TableRow>
                <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                    <strong>№</strong>
                </TableCell>
                {daysOfWeek.map(day => (
                    <TableCell key={day} align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthDays}}>
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
                    <TableCell align="center" style={{...cellBorderStyle, width: cellWidthStyle.widthNumber}}>
                        {index + 1}
                    </TableCell>
                    {daysOfWeek.map(day => (
                        <TableCell key={`${lesson}-${day}`} align="center"
                                   style={{...cellBorderStyle, width: cellWidthStyle.widthDays}}>
                            {tableData[lesson] && tableData[lesson][day] ?
                                tableData[lesson][day] : ""}
                        </TableCell>
                    ))}
                </TableRow>
            ))
        );
    };

    return (
        <Box display="flex" flexDirection="column" alignItems="center">
            <Box
                sx={{
                    width: "1020px",
                    border: '1px solid #ddd',
                    borderRadius: '8px',
                    backgroundColor: '#ffffff',
                    padding: '20px',
                }}
            >

                <Box display="flex" flexDirection="column" alignItems="center">
                    {loading ? (
                        <Loading/>
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

            <ErrorSnackbar
                open={showSnackbar}
                onClose={() => setShowSnackbar(false)}
                message={errorMessage}
            />
        </Box>
    );
};

export default SubjectSchedule;
