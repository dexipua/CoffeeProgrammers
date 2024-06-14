import React, {useEffect, useState} from 'react';
import UserNewsService from '../../../services/UserNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import UserNewsBox from '../../common/news/user_news/UserNewsBox';
import {Box, Container, Grid} from '@mui/material';

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
            <ApplicationBar />
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
                        backgroundColor: '#f8f8f8',
                        width: '100%',
                        maxWidth: '800px',
                    }}
                >
                    <Grid container justifyContent="center">
                        {newsList.map((newsItem) => (
                            <Grid item xs={12} key={newsItem.id}>
                                <UserNewsBox text={newsItem.text} time={newsItem.time} />
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
};

export default UserNews;
