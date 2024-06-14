import AccountService from "../../../services/UserService";
import React, {useEffect, useState} from "react";
import ApplicationBar from "../../layouts/ApplicationBar";
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import AccountContainer from "../../common/user/AccountContainer";
import Loading from "../../layouts/Loading";
import {Grid} from "@mui/material";
import AverageMarkView from "../../common/student/AverageMarkView";

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

    const handleNameUpdate = (newFirstName, newLastName) => {
        console.log(newFirstName + " " + newLastName)
        setAccount(prevAccount => ({
            ...prevAccount,
                firstName: newFirstName,
                lastName: newLastName
        }))
    }

    if (loading) {
        return <Loading/>;
    }

    const role = localStorage.getItem('role');

    return (
        <>
            <ApplicationBar/>
            <Box mt="80px" ml="60px" mr="60px">

                <Grid container spacing={2} alignItems="flex-start">

                    <Grid item xs={12} sm={6} md={4}>
                        <Box>
                            <AccountContainer
                                edit={true}
                                topic={"Account"}
                                user= {account}
                                onNameUpdate={handleNameUpdate}
                            />
                            {role === "STUDENT" && (
                                <AverageMarkView
                                    averageMark={account.averageMark}
                                />
                            )}
                        </Box>

                    </Grid>

                    <Grid item xs={12} sm={6} md={8}>
                        <Box
                            sx={{
                                width: '100%',
                                border: '1px solid #ddd',
                                borderRadius: '8px',
                                backgroundColor: '#ffffff',
                                textAlign: 'center',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                mt: 2
                            }}
                        >
                            <Typography mt="10px" variant="h6" component="h3">My Subjects</Typography>
                            <Box
                                sx={{
                                    width: '100%',
                                    display: 'flex',
                                    justifyContent: 'center',
                                }}
                            >
                                <SubjectWithTeacherList subjects={account.subjects}/>
                            </Box>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
}

export default Account;
