import axios from 'axios';

const API_URL = 'http://localhost:9091/student_news';

class UserNewsService {
    async getMyNews(token) {
        try {
            const response = await axios.get(`${API_URL}/myNews`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getMyNews:', error);
            throw error;
        }
    }
}

export default new UserNewsService();