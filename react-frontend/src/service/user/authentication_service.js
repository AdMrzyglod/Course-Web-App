import axios from "axios";
import {API_BASE_URL} from "../app";


export const fetchRegisterUser = async (userData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/auth/signup`,userData);

    } catch (error) {
        throw error
    }
};




export const fetchLoginUser = async (userData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/auth/login`,userData);

    } catch (error) {
        throw error
    }
};

