import axios from 'axios';

const API_URL = 'http://localhost:9091';

class UserService {

    async getAccount(token) {
        try {
            const response = await axios.get(`${API_URL}/account`, {
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

    async getBookmark(token) {
        try {
            const response = await axios.get(`${API_URL}/bookmark`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching bookmarks:', error);
            throw error;
        }
    }

    async login(username, password) {
        try {
            const
                response = await axios.post(`${API_URL}/api/auth/login`, {
                    username,
                    password,
                });

            const roleId = await this.getRoleIdByRoleAndUserId(response.data.accessToken)

            localStorage.setItem('roleId', roleId)
            localStorage.setItem('jwtToken', response.data.accessToken);
            localStorage.setItem('role', response.data.role)
        } catch
            (error) {
            throw new Error('Login failed. Check your credentials.');
        }
    }

    async getRoleIdByRoleAndUserId(token) {
        try {
            const response = await axios.get(`${API_URL}/roleId`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching role ID:', error);
            throw error;
        }
    }
}

export default new UserService();
