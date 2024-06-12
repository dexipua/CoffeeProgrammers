import SubjectData from "./SubjectData";
import Box from "@mui/material/Box";
import {Link} from "react-router-dom";

const SubjectList = ({subjects}) => {
    return (
        <div>
            {subjects.map((subject) => (
                <div>
                    <Link to={`/subjects/${subject.id}`} style={{textDecoration: 'none', color: 'inherit'}}>
                        <Box
                            width={150}
                            height={40}
                            display="flex"
                            flexDirection="column"
                            justifyContent="center"
                            alignItems="center"
                            gap={2}
                            p={2}
                            my={1}
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
                            <SubjectData
                                subject={subject}/>
                        </Box>
                    </Link>
                </div>
            ))}
        </div>
    )
}

export default SubjectList