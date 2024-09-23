import {
    activeCourseCode,
    addCourse, addCourseImage,
    addSubsectionFile,
    fetchCategories, fetchCodesCreate, fetchCourseActive,
    fetchCourseDetail, fetchCourseImage,
    fetchCourses, fetchSubsectionFile, fetchUserCourses, fetchUserCreateCourses
} from "../service/course_service";


export const getCategories = async () => {

    try {

        const response = await fetchCategories();
        return response.data;

    } catch (error) {
        throw error
    }
};



export const getCourses = async () => {

    try {

        const response = await fetchCourses();
        return response.data;

    } catch (error) {
        throw error
    }
};


export const getUserCourses = async () => {

    try {

        const response = await fetchUserCourses();
        return response.data;

    } catch (error) {
        throw error
    }
};


export const getUserCreateCourses = async () => {

    try {

        const response = await fetchUserCreateCourses();
        return response.data;

    } catch (error) {
        throw error
    }
};



export const getCourseDetails = async (courseID) => {

    try {

        const response = await fetchCourseDetail(courseID);
        return response.data;

    } catch (error) {
        throw error
    }
};


export const getCourseImage = async (courseID) => {
    try {
        const response = await fetchCourseImage(courseID);
        return response.data;
    } catch (error) {
        console.error(error);
    }
};


export const getSubsectionFile = async (subsectionID) => {
    try {
        const response = await fetchSubsectionFile(subsectionID);
        return response.data;
    } catch (error) {
        console.error(error);
    }
};


export const addCourseData = async (courseData, sections, image) => {

    try {

        const responseCourse = await addCourse(courseData)

        if(responseCourse.status===201){
            const courseID = responseCourse.data.courseID;

            const formImage = new FormData();
            formImage.append("imageFile", image)

            await addCourseImage(courseID,formImage);

            const details = await getCourseDetails(courseID);

            sections.forEach((section)=>{
                const sectionFromResponse = details.sections.find((s)=>s.number===section.number)
                if(sectionFromResponse!==undefined){
                    section.subsections.forEach(async (subsection)=>{
                        const subsectionFromResponse = sectionFromResponse.subsections.find((s)=>s.number===subsection.number)
                        const subsectionID = subsectionFromResponse.subsectionID;

                        const formFile = new FormData();
                        formFile.append("file", subsection.fileData);

                        await addSubsectionFile(subsectionID, formFile);
                    })
                }
            })

        }
        else{
            throw new Error("Course was not added")
        }


    } catch (error) {
        console.error(error);
    }
}


export const activeCourseCodeData = async (activeCodeData) =>{
    try {
        const response = await activeCourseCode(activeCodeData);
        return response;
    } catch (error) {
        console.error(error);
    }
}


export const getCourseActive = async (courseID) => {

    try {

        const response = await fetchCourseActive(courseID);
        return response.data;

    } catch (error) {
        throw error
    }
};



export const codeCreate = async (courseID, codeCreateData) => {

    try {

        const response = await fetchCodesCreate(courseID, codeCreateData);
        return response.data;

    } catch (error) {
        throw error
    }
};

