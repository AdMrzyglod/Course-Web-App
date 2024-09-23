import React from 'react';
import {getAllOrders} from "../../data/order_data";
import CourseOrders from "../course-orders/course-orders";


export default function CourseAllOrders(){

    return <CourseOrders getOrders={getAllOrders} panel={"EMPLOYEE"}/>
}

