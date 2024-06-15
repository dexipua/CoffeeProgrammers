import React, {useEffect, useState} from 'react';
import SchoolNewsService from '../../../services/SchoolNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";
import {Box, Container, Grid, Typography} from "@mui/material";
import SchoolNewsBox from "../../common/news/school_news/SchoolNewsBox";
import CreateNewsAddButton from "../../common/news/school_news/SchoolNewsCreateButton";

const SchoolNews = () => {
    const [newsList, setNewsList] = useState([]);

    const token = localStorage.getItem('jwtToken')
    const role = localStorage.getItem('role');

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

    const handleUpdate = async (newsId, updatedNews) => {
        const updatedNewsList = newsList.map((news) =>
            news.id === newsId ? updatedNews : news
        )
        setNewsList(updatedNewsList)
    }

    const handleCreate = async (createdNews) => {
        setNewsList([...newsList, createdNews])
    }

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
                        width: '90%',
                        minWidth: "700px",
                        maxWidth: '800px',
                    }}
                >
                {role === "CHIEF_TEACHER" &&
                    <Box display="flex" justifyContent="center" mb={2}>
                        <CreateNewsAddButton onCreate={handleCreate} />
                    </Box>
                }
                </Box>
                <Box
                    sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        padding: '16px',
                        backgroundColor: '#FFF',
                        width: '90%',
                        minWidth: "700px",
                        maxWidth: '800px',
                    }}
                >
                    <Grid container justifyContent="center">
                        {newsList.length > 0 ? (newsList.map((newsItem) => (
                            <Grid item xs={12} key={newsItem.id}>
                                <SchoolNewsBox
                                    role={role}
                                    text={newsItem.text}
                                    time={newsItem.time}
                                    newsId={newsItem.id}
                                    updateFunction={handleUpdate}
                                    deleteFunction={() =>
                                        handleDelete(newsItem.id)}
                                />
                            </Grid>
                        ))) : (
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

export default SchoolNews;
