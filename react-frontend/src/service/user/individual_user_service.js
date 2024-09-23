import axios from 'axios';
import {API_BASE_URL} from "../app";





export const fetchIndividualUserDataByPlatformUserID = async (platformUserID) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/individualUser/${platformUserID}`);

    } catch (error) {

        throw error;

    }
};


export const fetchIndividualUserAccountData = async () => {

    try {

        return await axios.post(`${API_BASE_URL}/api/individualUser/currentUser`);

    } catch (error) {

        throw error;

    }
};














