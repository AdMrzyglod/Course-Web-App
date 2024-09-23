import React from 'react';
import CourseUserData from "../course-user-data/course-user-data";
import {getAdminAccountData} from "../../data/user/admin_data";



export default function CourseAdminData(){

    return <CourseUserData getUserAccountData={getAdminAccountData} panel={"ADMIN"}/>
}





