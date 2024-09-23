import React from 'react';
import {getCourses} from "../../data/course_data";
import CourseCourseList from "../course-course-list/course-course-list";


export default function CourseList(){

    return <CourseCourseList getCoursesData={getCourses}/>
}

