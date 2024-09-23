import axios from 'axios';
import {API_BASE_URL} from "../app";



export const fetchPlatformUsersByRole = async (roleData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/platformUser/findByRole`, roleData);

    } catch (error) {

        throw error;

    }
};















