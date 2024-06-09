import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SubjectData from "./SubjectData";
import TeacherData from "../teacher/TeacherData";
import {Link} from "react-router-dom";

const SubjectWithTeacherForProfile = ({ subject: { id, name, teacher } }) => {
    return (
        <Box
            width={300}
            height="auto"
            my={2}
            display="flex"
            flexDirection="column"
            justifyContent="flex-start"
            alignItems="flex-start"
            gap={1}
            p={2}
            sx={{
                border: '1px solid #ddd',
                borderRadius: '8px',
                backgroundColor: '#ffffff',
            }}
        >
            <Box width="100%" display="flex" justifyContent="flex-start" alignItems="center">
                <Typography variant="subtitle1" sx={{ margin: 0, fontWeight: 'bold', color: '#333' }}>
                    Name: <br />
                    <SubjectData subject={{ id, name }} />
                </Typography>
            </Box>

            <Box width="100%" display="flex" alignItems="center" justifyContent="flex-start" gap={0.5}>
                <Typography variant="body2" sx={{ margin: 0, color: '#555' }}>
                    Teacher: <br />
                    <Link to={`/teacher/${teacher.id}`} style={{ color: 'inherit' }}>
                        <TeacherData teacher={teacher} />
                    </Link>
                </Typography>
            </Box>
        </Box>
    );
}

export default SubjectWithTeacherForProfile;
