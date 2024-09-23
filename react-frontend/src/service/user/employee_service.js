import axios from "axios";
import {API_BASE_URL} from "../app";


export const fetchAddEmployee = async (employeeData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/employee/`,employeeData);

    } catch (error) {
        throw error
    }
};


export const fetchEmployeeDataByPlatformUserID = async (platformUserID) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/employee/${platformUserID}`);

    } catch (error) {

        throw error;

    }
};


export const fetchEmployeeAccountData = async () => {

    try {

        return await axios.post(`${API_BASE_URL}/api/employee/currentUser`);

    } catch (error) {

        throw error;

    }
};




export const putUpdateEmployeeState = async (state) => {

    try {

        return await axios.put(`${API_BASE_URL}/api/employee/updateState`,state);

    } catch (error) {
        throw error
    }
};