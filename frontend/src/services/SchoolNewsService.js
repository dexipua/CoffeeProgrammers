import axios from 'axios';

const API_URL = 'http://localhost:9091/schoolNews';

class SchoolNewsService {
    async create(schoolNewsRequest, token) {
        try {
            const response = await axios.post(`${API_URL}/create`, schoolNewsRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error create:', error);
            throw error;
        }
    }

    async update(id, schoolNewsRequest, token) {
        try {
            const response = await axios.put(`${API_URL}/update/${id}`, schoolNewsRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error update:', error);
            throw error;
        }
    }

    async delete(id, token) {
        try {
            await axios.delete(`${API_URL}/delete/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error delete:', error);
            throw error;
        }
    }

    async getAll(token) {
        try {
            const response = await axios.get(`${API_URL}/getAll`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getAll:', error);
            throw error;
        }
    }
}

export default new SchoolNewsService();
