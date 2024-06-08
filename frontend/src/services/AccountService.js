import axios from 'axios';

const API_URL = 'http://localhost:9091/account';

class AccountService {

    async getAccount(token) {
        try {
            const response = await axios.get(`${API_URL}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('API response:', response.data);
            return response.data;
        } catch (error) {
            console.error('Error getAllBySubjectId:', error);
            throw error;
        }
    }
}

export default new AccountService();
