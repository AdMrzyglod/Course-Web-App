import axios from 'axios';
import {API_BASE_URL} from "./app";





export const addOrder = async (orderData) =>{

    try{

        return await axios.post(`${API_BASE_URL}/api/order/`,orderData);

    } catch (error) {

        throw error;
    }
}


export const fetchOrderHistory = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/order/list/`);

    } catch (error) {

        throw error;

    }
};

export const fetchAllOrders = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/order/all/`);

    } catch (error) {

        throw error;

    }
};



export const fetchOrderHistoryCourseCodes = async (orderID, courseID) => {

    try {
        return await axios.get(`${API_BASE_URL}/api/order/${orderID}/codes/${courseID}`);

    } catch (error) {

        throw error;

    }
};
















