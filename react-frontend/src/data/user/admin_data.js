import {fetchAddAdmin, fetchAdminAccountData, putUpdateAdminState} from "../../service/user/admin_service";



export const addAdminData = async (adminData) => {

    try {

        const response = await fetchAddAdmin(adminData);
        return response.status;

    } catch (error) {
        throw error
    }
};



export const getAdminAccountData = async () => {

    try {

        const response = await fetchAdminAccountData();
        return response.data;

    } catch (error) {
        throw error
    }
};


export const updateAdminState = async (state) => {

    try {

        const response = await putUpdateAdminState(state);
        return response;

    } catch (error) {
        throw error
    }
};
