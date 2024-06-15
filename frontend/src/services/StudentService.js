import axios from 'axios';

const API_URL = 'http://localhost:9091/students';

class StudentService {

    async create(userRequest, token) {
        try {
            const response = await axios.post(`${API_URL}/create`, userRequest, {
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

    async getById(id, token) {
        try {
            const response = await axios.get(`${API_URL}/getById/${id}`, {
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

    async update(id, userRequest, token) {
        try {
            const response = await axios.put(`${API_URL}/update/${id}`, userRequest, {
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

    async getByTeacherId(teacherId, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllByTeacherId/${teacherId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getByTeacherId:', error);
            throw error;
        }
    }

    async getByName(firstName, lastName, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllByName/`, {
                params: {
                    first_name: firstName,
                    last_name: lastName
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getByName:', error);
            throw error;
        }
    }

    async getStudentsCount(token) {
        try {
            const response = await axios.get(`${API_URL}/count`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getting students count:', error);
            throw error;
        }
    }

    async findAllBySubjectsIdIsNot(subjectId, token) {
        try {
            const response = await axios.get(`${API_URL}/subjectIdIsNot/${subjectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error finding students by subject id:', error);
            throw error;
        }
    }
}

export default new StudentService();
