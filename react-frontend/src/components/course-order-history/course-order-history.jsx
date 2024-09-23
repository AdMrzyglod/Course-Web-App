import React from 'react';
import {getOrderHistory} from "../../data/order_data";
import CourseOrders from "../course-orders/course-orders";


export default function CourseOrderHistory(){

    return <CourseOrders getOrders={getOrderHistory} panel={"USER"}/>
}

