import React from 'react';
import CourseUserData from "../course-user-data/course-user-data";
import {getEmployeeAccountData} from "../../data/user/employee_data";



export default function CourseEmployeeData(){

    return <CourseUserData getUserAccountData={getEmployeeAccountData} panel={"EMPLOYEE"}/>
}





