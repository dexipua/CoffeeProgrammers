import axios from 'axios';

const API_URL = 'http://localhost:9091/marks';

class MarkService {

    async create(markRequest, subjectId, studentId, token) {
        try {
            const response = await axios.post(`${API_URL}/subject/${subjectId}/student/${studentId}/createMark`, markRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error createMark:', error);
            throw error;
        }
    }

    async getById(id, token) {
        try {
            const response = await axios.get(`${API_URL}/getById/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getByMarkId:', error);
            throw error;
        }
    }

    async update(id, markRequest, token) {
        try {
            const response = await axios.put(`${API_URL}/update/${id}`, markRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error updateMark:', error);
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
            console.error('Error deleteMark:', error);
            throw error;
        }
    }

    async getByStudentId(studentId, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllByStudentId/${studentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getById:', error);
            throw error;
        }
    }

    async getBySubjectId(subjectId, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllBySubjectId/${subjectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getAllBySubjectId:', error);
            throw error;
        }
    }
}

export default new MarkService();
