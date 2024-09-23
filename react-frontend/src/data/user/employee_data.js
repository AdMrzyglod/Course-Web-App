import {fetchAddEmployee, fetchEmployeeAccountData, putUpdateEmployeeState} from "../../service/user/employee_service";



export const addEmployeeData = async (employeeData) => {

    try {

        const response = await fetchAddEmployee(employeeData);
        return response.status;

    } catch (error) {
        throw error
    }
};



export const getEmployeeAccountData = async () => {

    try {

        const response = await fetchEmployeeAccountData();
        return response.data;

    } catch (error) {
        throw error
    }
};



export const updateEmployeeState = async (state) => {

    try {

        const response = await putUpdateEmployeeState(state);
        return response;

    } catch (error) {
        throw error
    }
};