import axios from 'axios';

const API_URL = 'http://localhost:9091/timeTable';

class SubjectDateService {
    async create(subjectDateRequest, subjectId, token) {
        try {
            const response = await axios.post(`${API_URL}/create/subject/${subjectId}`, subjectDateRequest, {
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

    async update(subjectDateId, subjectDateRequest, token) {
        try {
            const response = await axios.put(`${API_URL}/update/${subjectDateId}`, subjectDateRequest, {
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

    async delete(subjectDateId, token) {
        try {
            await axios.delete(`${API_URL}/delete/${subjectDateId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error delete:', error);
            throw error;
        }
    }

    async getById(subjectDateId, token) {
        try {
            const response = await axios.get(`${API_URL}/getById/${subjectDateId}`, {
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

    async getAllByStudentId(studentId, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllByStudent/${studentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getting all subject dates by student ID:', error);
            throw error;
        }
    }

    async getAllBySubjectId(subjectId, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllBySubject/${subjectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getting all subject dates by subject ID:', error);
            throw error;
        }
    }
}

export default new SubjectDateService;