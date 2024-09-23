import React from 'react';
import {getUserCourses} from "../../data/course_data";
import CourseCourseList from "../course-course-list/course-course-list";


export default function CourseUserCourseList(){

    return <CourseCourseList getCoursesData={getUserCourses}/>
}

