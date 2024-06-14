import React, {useEffect, useState} from 'react';
import SchoolNewsService from '../../../services/SchoolNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import {Box, Container, Grid, Typography} from "@mui/material";
import SchoolNewsBox from "../../common/news/school_news/SchoolNewsBox";

const SchoolNews = () => {
    const [newsList, setNewsList] = useState([]);

    const token = localStorage.getItem('jwtToken')
    useEffect(() => {
        const fetchData = async () => {
            const response = await SchoolNewsService.getAll(token);
            setNewsList(response);
        };
        fetchData();
    }, [token]);

    const handleDelete = async (newsId) => {
        await SchoolNewsService.delete(newsId, token)
        const filteredNews = newsList.filter((news) => news.id !== newsId)
        setNewsList(filteredNews)
    }

    const handleUpdate = async (newsId, text) => {
        const updatedMark = await SchoolNewsService.update(
            newsId,
            {text: ""},
            localStorage.getItem('jwtToken'))
        console.log(updatedMark)
        const updatedNews = newsList.map((news) =>
            news.id === newsId ? updatedMark : news
        )
        setNewsList(updatedNews)
    }

    return (
        <Container>
            <ApplicationBar />
            <Box
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"

                mt="80px"
            >
                <Typography variant="h6" component="h1" gutterBottom>
                    School news
                </Typography>
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
                                <SchoolNewsBox
                                    text={newsItem.text}
                                    time={newsItem.time}
                                    updateFunction={(text) =>
                                        handleUpdate(newsItem.id, text)}
                                    deleteFunction={() =>
                                        handleDelete(newsItem.id)}
                                />
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
};

export default SchoolNews;
