import React, {useEffect, useState} from 'react';
import UserNewsService from '../../../services/UserNewsService';
import ApplicationBar from "../../layouts/app_bar/ApplicationBar";

const UserNews = () => {
    const [news, setNews] = useState([]);

    const token = localStorage.getItem('jwtToken')

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await UserNewsService.getMyNews(token);
                setNews(response);
            } catch (error) {
                console.error('Error fetching user news:', error);
            }
        };

        fetchData();
    }, [token]);

    return (
        <div>
            <ApplicationBar />
            <h1 style={{marginTop: "80px"}}>My News</h1>
            {news.map((newsItem) => (
                <div key={newsItem.id}>
                    <h3>{newsItem.title}</h3>
                    <p>{newsItem.content}</p>
                    <p>{new Date(newsItem.time).toLocaleString()}</p>
                </div>
            ))}
        </div>
    );
};

export default UserNews;
