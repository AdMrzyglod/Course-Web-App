import axios from "axios";
import {API_BASE_URL} from "../app";


export const fetchAddAdmin = async (adminData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/admin/`,adminData);

    } catch (error) {
        throw error
    }
};



export const fetchAdminDataByPlatformUserID = async (platformUserID) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/admin/${platformUserID}`);

    } catch (error) {

        throw error;

    }
};



export const fetchAdminAccountData = async () => {

    try {

        return await axios.post(`${API_BASE_URL}/api/admin/currentUser`);

    } catch (error) {

        throw error;

    }
};


export const putUpdateAdminState = async (state) => {

    try {

        return await axios.put(`${API_BASE_URL}/api/admin/updateState`,state);

    } catch (error) {
        throw error
    }
};
