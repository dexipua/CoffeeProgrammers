import axios from 'axios';

const API_URL = 'http://localhost:9091';

class UserService {

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

            const accessToken = response.data.accessToken;
            const role = response.data.role;
            const roleId = await this.getRoleIdByRoleAndUserId(accessToken)

            localStorage.setItem('jwtToken', accessToken);
            localStorage.setItem('role',role)
            localStorage.setItem('roleId', roleId)
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
