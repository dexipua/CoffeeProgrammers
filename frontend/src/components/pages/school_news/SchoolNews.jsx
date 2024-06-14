import React, { useState, useEffect } from 'react';
import SchoolNewsService from '../../../services/SchoolNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";

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

    return (
        <div>
            <ApplicationBar />
            <h1 style={{marginTop: "80px"}}>School News</h1>
            {newsList.map((news) => (
                <div key={news.id}>
                    <h3>{news.title}</h3>
                    <p>{news.time}</p>
                </div>
            ))}
        </div>
    );
};

export default SchoolNews;
