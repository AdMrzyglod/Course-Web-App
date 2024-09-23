import {addOrder, fetchAllOrders, fetchOrderHistory, fetchOrderHistoryCourseCodes} from "../service/order_service";


export const addOrderData = async (orderData) => {

    try {

        const responseOrder = await addOrder(orderData)

        if(responseOrder.status===201){
            return true;
        }
        else{
            throw new Error("Order was not added");
        }


    } catch (error) {
        console.error(error);
        return false;
    }
}



export const getOrderHistory = async () => {

    try {

        const response = await fetchOrderHistory();
        return response.data;

    } catch (error) {
        throw error
    }
};


export const getAllOrders = async () => {

    try {

        const response = await fetchAllOrders();
        return response.data;

    } catch (error) {
        throw error
    }
};



export const getOrderHistoryCourseCodes = async (orderID, courseID) => {

    try {

        const response = await fetchOrderHistoryCourseCodes(orderID, courseID);
        return response.data;

    } catch (error) {
        throw error
    }
};



