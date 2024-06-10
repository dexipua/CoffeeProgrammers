import AccountService from "../../../services/AccountService";
import {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import {Link} from "react-router-dom";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import AccountContainer from "../../common/user/AccountContainer";

const Account = () => {
    const [account, setAccount] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchAccount = async () => {
            try {
                const response = await AccountService.getAccount(token);
                setAccount(response);
            } catch (error) {
                console.error('Error fetching account:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchAccount().then();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    const role = localStorage.getItem('role');

    return (
        <div style={{ display: 'flex' }}>
            <div style={{ flex: 1, marginRight: '20px', marginTop: '125px' }}>
                <ButtonAppBar />
                <Box
                    width={300}
                    height={100}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    alignItems="center"
                    gap={2}
                    p={2}
                    sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        backgroundColor: '#FFFFFFFF',
                        transition: 'transform 0.2s, box-shadow 0.2s',
                    }}
                >
                    <Box height="30px" display="flex" justifyContent="center" alignItems="center">
                        <Typography variant="h6" align="center">
                            My Account
                        </Typography>
                    </Box>
                    <Box height="70px" display="flex" alignItems="center" justifyContent="center" mt={-2}>
                        <AccountContainer
                            user={account}/>
                    </Box>
                </Box>
                <Box
                    width={300}
                    height={40}
                    my={1}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    alignItems="center"
                    gap={2}
                    p={2}
                    sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        backgroundColor: '#FFFFFFFF',
                        transition: 'transform 0.2s, box-shadow 0.2s',
                    }}
                >
                    {role === "STUDENT" ? (
                        <Link to={`/marks/getAllByStudentId/${account.id}`}>
                            <div>My marks</div>
                        </Link>
                    ) : role === "CHIEF_TEACHER" ? (
                        <Link to={"/admin"}>
                            <div>Admin panel</div>
                        </Link>
                    ) : null}
                </Box>
            </div>
            <div style={{ flex: 3, marginTop: '70px' }}>
                <Typography variant="h3" align="center">Subjects</Typography>
                <div>
                    <SubjectWithTeacherList subjects={account.subjects} />
                </div>
            </div>
        </div>
    );
}

export default Account;
