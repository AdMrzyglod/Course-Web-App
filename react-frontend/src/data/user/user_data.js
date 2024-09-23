import {
    fetchPlatformUsersByRole
} from "../../service/user/user_service";
import {
    fetchIndividualUserDataByPlatformUserID
} from "../../service/user/individual_user_service";
import {fetchEmployeeDataByPlatformUserID} from "../../service/user/employee_service";
import {fetchAdminDataByPlatformUserID} from "../../service/user/admin_service";



export const getPlatformUsersByRole = async (roleData) => {

    try {

        const response = await fetchPlatformUsersByRole(roleData);
        return response.data;

    } catch (error) {
        throw error
    }
};



export const getPlatformUsersByRoleAndPlatformUserID = async (role, platformUserID) => {

    try {
        let response = null;
        if(role === "USER"){
            response = await fetchIndividualUserDataByPlatformUserID(platformUserID);
        }
        else if(role === "EMPLOYEE"){
            response = await fetchEmployeeDataByPlatformUserID(platformUserID);
        }
        else if(role === "ADMIN"){
            response = await fetchAdminDataByPlatformUserID(platformUserID);
        }

        return response ? response.data: null;

    } catch (error) {
        throw error
    }
};