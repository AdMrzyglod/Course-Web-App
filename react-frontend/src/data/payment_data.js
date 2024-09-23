import {addPayment, deletePayment, fetchPayments, fetchUserPayments} from "../service/payment_service";



export const getPayments = async () => {

    try {

        const response = await fetchPayments();
        return response.data;

    } catch (error) {
        throw error
    }
};

export const getUserPayments = async () => {

    try {

        const response = await fetchUserPayments();
        return response.data;

    } catch (error) {
        throw error
    }
};


export const addPaymentData = async (paymentData) => {

    try {

        const responsePayment = await addPayment(paymentData)
        return responsePayment.status===201;

    } catch (error) {
        console.error(error);
    }
}


export const deletePaymentData = async (paymentID) => {

    try {

        await deletePayment(paymentID);

    } catch (error) {
        throw error;
    }
}




