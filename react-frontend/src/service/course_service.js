import axios from 'axios';
import {API_BASE_URL} from "./app";


export const fetchCategories = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/category/`);

    } catch (error) {
        throw error
    }
};


export const fetchCourses = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/course/`);

    } catch (error) {

        throw error;

    }
};


export const fetchUserCourses = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/course/list/`);

    } catch (error) {

        throw error;

    }
};


export const fetchUserCreateCourses = async () => {

    try {

        return await axios.get(`${API_BASE_URL}/api/course/list-create/`);

    } catch (error) {

        throw error;

    }
};


export const fetchCourseDetail = async (courseID) =>{

    try{

        return await axios.get(`${API_BASE_URL}/api/course/${courseID}`);

    } catch (error) {

        throw error;
    }
}


export const fetchCourseImage = async (courseID) => {
    try {
        return await axios.get(`${API_BASE_URL}/api/course/image/${courseID}`, {
            responseType: "blob",
        });
    } catch (error) {
        console.error(error);
    }
};


export const fetchSubsectionFile = async (subsectionID) => {
    try {
        return await axios.get(`${API_BASE_URL}/api/course/file/${subsectionID}`, {
            responseType: "blob",
        });
    } catch (error) {
        console.error(error);
    }
};



export const addCourse = async (courseData) =>{

    try{

        return await axios.post(`${API_BASE_URL}/api/course/`,courseData);

    } catch (error) {

        throw error;
    }
}


export const addCourseImage = async (courseID, imageData) =>{

    try{
        return await axios.post(`${API_BASE_URL}/api/course/image/${courseID}`,imageData,{
            headers: {
                'content-type': 'multipart/form-data'
            }
        });

    } catch (error) {

        throw error;
    }
}


export const addSubsectionFile = async (subsectionID, fileData) =>{

    try{
        return await axios.post(`${API_BASE_URL}/api/course/file/${subsectionID}`,fileData,{
            headers: {
                'content-type': 'multipart/form-data'
            }
        });

    } catch (error) {

        throw error;
    }
}


export const activeCourseCode = async (activeCodeData) =>{

    try{
        return await axios.post(`${API_BASE_URL}/api/course/activeCode`,activeCodeData);

    } catch (error) {

        throw error;
    }
}



export const fetchCourseActive = async (courseID) => {
    try {
        return await axios.get(`${API_BASE_URL}/api/course/${courseID}/courseActive`);
    } catch (error) {
        console.error(error);
    }
};


export const fetchCodesCreate = async (courseID, codeCreateData) => {
    try {
        return await axios.post(`${API_BASE_URL}/api/course/${courseID}/createCodes`, codeCreateData);
    } catch (error) {
        console.error(error);
    }
};














