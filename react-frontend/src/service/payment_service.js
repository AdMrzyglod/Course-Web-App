import axios from 'axios';
import {API_BASE_URL} from "./app";




export const fetchPayments = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/payment/`);

    } catch (error) {

        throw error;

    }
};



export const fetchUserPayments = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/payment/list/`);

    } catch (error) {

        throw error;

    }
};



export const addPayment = async (paymentData) => {

    try {

        return await axios.post(`${API_BASE_URL}/api/payment/`, paymentData);

    } catch (error) {

        throw error;

    }
};


export const deletePayment = async (paymentID) => {

    try {

        return await axios.delete(`${API_BASE_URL}/api/payment/${paymentID}`);

    } catch (error) {

        throw error;

    }
};















