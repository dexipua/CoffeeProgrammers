import axios from 'axios';

const API_URL = 'http://localhost:9091/subjects';

class SubjectService {

    async create(subjectRequest, token) {
        try {
            const response = await axios.post(`${API_URL}/create`, subjectRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error creating subject:', error);
            throw error;
        }
    }

    async getById(subjectId, token) {
        try {
            const response = await axios.get(`${API_URL}/getById/${subjectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getting subject by ID:', error);
            throw error;
        }
    }

    async update(subjectId, subjectRequest, token) {
        try {
            const response = await axios.put(`${API_URL}/update/${subjectId}`, subjectRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error updating subject:', error);
            throw error;
        }
    }

    async delete(subjectId, token) {
        try {
            await axios.delete(`${API_URL}/delete/${subjectId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error deleting subject:', error);
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
            console.error('Error getting all subjects:', error);
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
            console.error('Error getting subjects by teacher ID:', error);
            throw error;
        }
    }

    async setTeacher(subjectId, teacherId, token) {
        try {
            await axios.patch(`${API_URL}/update/${subjectId}/setTeacher/${teacherId}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error setting teacher for subject:', error);
            throw error;
        }
    }

    async deleteTeacher(subjectId, token) {
        try {
            await axios.patch(`${API_URL}/update/${subjectId}/deleteTeacher`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error deleting teacher from subject:', error);
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
            console.error('Error getting subjects by student ID:', error);
            throw error;
        }
    }

    async addStudent(subjectId, studentId, token) {
        try {
            await axios.patch(`${API_URL}/update/${subjectId}/addStudent/${studentId}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error adding student to subject:', error);
            throw error;
        }
    }

    async deleteStudent(subjectId, studentId, token) {
        try {
            await axios.patch(`${API_URL}/update/${subjectId}/deleteStudent/${studentId}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        } catch (error) {
            console.error('Error deleting student from subject:', error);
            throw error;
        }
    }

    async getByNameContaining(name, token) {
        try {
            const response = await axios.get(`${API_URL}/getAllByName/`, {
                params: {
                    subject_name: name
                },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error getting subjects by name containing:', error);
            throw error;
        }
    }
}

export default new SubjectService();
