import React from 'react';
import CourseUserData from "../course-user-data/course-user-data";
import {getIndividualUserAccountData} from "../../data/user/individual_user_data";



export default function CourseIndividualUserData(){

    return <CourseUserData getUserAccountData={getIndividualUserAccountData} panel={"USER"}/>
}





