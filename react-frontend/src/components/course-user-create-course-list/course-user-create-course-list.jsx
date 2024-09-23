import React from 'react';
import {getUserCreateCourses} from "../../data/course_data";
import CourseCourseList from "../course-course-list/course-course-list";


export default function CourseUserCreateCourseList(){

    return <CourseCourseList getCoursesData={getUserCreateCourses}/>
}

