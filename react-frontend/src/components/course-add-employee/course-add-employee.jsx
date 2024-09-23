import React from 'react';
import {addEmployeeData} from "../../data/user/employee_data";
import CourseAddUser from "../course-add-user/course-add-user";


export default function CourseAddEmployee(){

    return (
        <CourseAddUser addUserData={addEmployeeData} type={"PRACOWNIKA"}/>
    )
}