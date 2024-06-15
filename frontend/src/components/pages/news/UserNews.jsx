import React, {useEffect, useState} from 'react';
import UserNewsService from '../../../services/UserNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import UserNewsBox from '../../common/news/user_news/UserNewsBox';
import {Box, Container, Grid} from '@mui/material';
import Typography from "@mui/material/Typography";

const UserNews = () => {
    const [newsList, setNewsList] = useState([]);

    const token = localStorage.getItem('jwtToken')

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await UserNewsService.getMyNews(token);
                setNewsList(response);
            } catch (error) {
                console.error('Error fetching user news:', error);
            }
        };

        fetchData();
    }, [token]);

    return (
        <Container>
            <ApplicationBar/>
            <Box
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"
            >
                <Box
                    sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        padding: '16px',
                        backgroundColor: '#fff',
                        width: '90%',
                        minWidth: "700px",
                        maxWidth: '800px',
                    }}
                >
                    <Grid container justifyContent="center">
                        {newsList.length > 0 ? newsList.map((newsItem) => (
                            <Grid item xs={12} key={newsItem.id}>
                                <UserNewsBox text={newsItem.text} time={newsItem.time}/>
                            </Grid>
                        )) : (
                            <Typography variant="body1" component="h3" gutterBottom>
                                No news at this time
                            </Typography>
                        )}
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
};

export default UserNews;
