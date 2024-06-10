import SubjectData from "./SubjectData";
import TeacherData from "../teacher/TeacherData";
import Box from "@mui/material/Box";
import AssignmentIndIcon from '@mui/icons-material/AssignmentInd';
import Typography from '@mui/material/Typography';
import {Link} from "react-router-dom";

const SubjectWithTeacherForList = ({ subjectResponse: { id, name, teacher } }) => {
    return (
        <Link to={`/subjects/getById/${id}`} style={{textDecoration: 'none', color: 'inherit'}}>
            <Box
                width={350}
                height={60}
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"
                gap={2}
                p={2}
                sx={{
                    border: '1px solid #ccc',
                    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                    borderRadius: '8px',
                    backgroundColor: '#FFFFFFFF',
                    transition: 'transform 0.2s, box-shadow 0.2s',
                    '&:hover': {
                        transform: 'scale(1.02)',
                        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                    },
                }}
            >
                <Box height="30px" display="flex" justifyContent="center" alignItems="center" mt={1}>
                    <Typography variant="h6" align="center">
                        <SubjectData subject={{id, name}}/>
                    </Typography>
                </Box>
                {teacher === null ?
                    <p>none</p>
                    :
                    <Box height="30px" display="flex" alignItems="center" justifyContent="center" gap={1} mt={-2}>
                        <AssignmentIndIcon style={{color: '#333'}}/>
                        <Typography variant="body1">
                            <TeacherData teacher={teacher}/>
                        </Typography>
                    </Box>
                }
            </Box>
        </Link>
    );
};

export default SubjectWithTeacherForList;
