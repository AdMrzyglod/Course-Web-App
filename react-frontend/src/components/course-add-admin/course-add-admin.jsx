import React from 'react';
import CourseAddUser from "../course-add-user/course-add-user";
import {addAdminData} from "../../data/user/admin_data";


export default function CourseAddAdmin(){
    return (
        <CourseAddUser addUserData={addAdminData} type={"ADMINISTRATORA"}/>
    )
}