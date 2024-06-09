import axios from 'axios';

const API_URL = 'http://localhost:9091/teachers';

class TeacherService {
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

    async getById(teacherId, token) {
        try {
            const response = await axios.get(`${API_URL}/getById/${teacherId}`, {
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

    async delete(teacherId, token) {
        try {
            await axios.delete(`${API_URL}/delete/${teacherId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error deleteById:', error);
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

    async getBySubjectName(subjectName, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllBySubjectName`, {
                params: {
                    subject_name: subjectName
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getBySubjectName:', error);
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
}

export default new TeacherService();
