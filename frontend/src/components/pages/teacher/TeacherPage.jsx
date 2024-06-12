import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import TeacherService from "../../../services/TeacherService";
import StudentService from "../../../services/StudentService";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import UserData from "../../common/user/UserData";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import TableHead from "@mui/material/TableHead";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import Table from "@mui/material/Table";
import TablePagination from "@mui/material/TablePagination";
import TablePaginationActions from "../../layouts/TablePaginationActions";
import TableRow from "@mui/material/TableRow";
import StudentSimple from "../../common/user/StudentSimple";
import TableContainer from "@mui/material/TableContainer";

const TeacherPage = () => {
    const { id } = useParams();
    const [loading, setLoading] = useState(true);
    const [loadingTwo, setLoadingTwo] = useState(true);
    const [teacher, setTeacher] = useState(null);
    const [students, setStudents] = useState(null);
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchData = async () => {
            try {
                const teacherResponse = await TeacherService.getById(id, token);
                setTeacher(teacherResponse);
                const studentResponse = await StudentService.getByTeacherId(id, token);
                setStudents(studentResponse);
            } catch (error) {
                console.error('Error fetching data:', error);
            } finally {
                setLoading(false);
                setLoadingTwo(false);
            }
        };

        fetchData();
    }, [id]);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const renderBody = () => {
        return students
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((student) => {
                return (
                    <TableRow key={student.id}>
                        <TableCell
                            align="center"
                            style={{ width: "10px" }}
                            sx={{borderBlock: 'none'}} // Flexbox to center content
                        >
                            <StudentSimple
                                id={student.id}
                                name={student.lastName + " " + student.firstName} />
                        </TableCell>
                    </TableRow>
                );
            });
    };

    if (loading || loadingTwo) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <ButtonAppBar />
            <Box
                display="flex"
                justifyContent="space-between"
                alignItems="flex-start"
                mt={9}
                ml={10}
            >
                {/* Teacher Information */}
                <Box
                    width="20%"
                    pr={2}
                >
                    <Typography variant="h6" align="center" marginLeft={5}>Teacher Information</Typography>
                    <Box
                        alignContent='center'
                        width='100%'
                        height={70}
                        p={2}
                        my={1}
                        sx={{
                            border: '1px solid #ccc',
                            borderRadius: '8px',
                            backgroundColor: '#FFFFFFFF',
                            transition: 'transform 0.2s, box-shadow 0.2s',
                        }}
                    >
                        <UserData
                            name={`${teacher.lastName} ${teacher.firstName}`}
                            email={teacher.email}
                        />
                    </Box>
                </Box>
                {/* List of Students */}
                <Box
                    width="25%"
                    pr={2}
                    pl={2}
                    ml={9}
                >
                    <TableContainer>
                        <Table sx={{ borderBlock: 'none' }}>
                            <TableHead>
                                <TableRow sx={{ borderBlock: 'none' }}>
                                    <TableCell sx={{borderBlock: 'none'}}>
                                        <Typography variant="h6" align="center">Students</Typography>
                                    </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {renderBody()}
                            </TableBody>
                        </Table>
                        <TablePagination
                            rowsPerPageOptions={[10]}
                            colSpan={2}
                            count={students.length}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            onPageChange={handleChangePage}
                            onRowsPerPageChange={handleChangeRowsPerPage}
                            ActionsComponent={TablePaginationActions}
                        />
                    </TableContainer>
                </Box>
                {/* List of Subjects */}
                <Box
                    width="35%"
                    pl={2}
                >
                    <Typography variant="h6" align="center">Subjects</Typography>
                    <SubjectWithTeacherList subjects={teacher.subjects} />
                </Box>
            </Box>
        </div>
    );
}
export default TeacherPage;
