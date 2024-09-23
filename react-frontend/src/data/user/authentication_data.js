import {
    fetchLoginUser,
    fetchRegisterUser
} from "../../service/user/authentication_service";


export const signup = async (userData) => {

    try {

        const response = await fetchRegisterUser(userData);
        return response.status;

    } catch (error) {
        throw error
    }
};

export const login = async (userData) => {

    try {

        const response = await fetchLoginUser(userData);
        return response.data;

    } catch (error) {
        throw error
    }
};