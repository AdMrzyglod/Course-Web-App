import React, {useState} from 'react';
import './course-order-details.css'
import { useLocation } from "react-router-dom";
import IconButton from '@mui/material/IconButton';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import CourseOrderCodes from "../course-order-codes/course-order-codes";

export default function CourseOrderDetails({order, panel}){

    let url= useLocation().pathname;

    const [display,setDisplay] = useState(false);

    const toggleDisplay = () => {
        setDisplay(!display);
    };


    return (
        <>
            <tr>
                <td>{order && order.platformOrderID ? order.platformOrderID : ""}</td>
                <td>{order && order.createTimestamp ? new Date(order.createTimestamp).toLocaleString() : ""}</td>
                <td>{order && order.numberOfAllCodes ? order.numberOfAllCodes : ""}</td>
                <td>{order && order.totalPrice ? order.totalPrice : ""} PLN</td>
                {
                    panel === "EMPLOYEE" ?
                        <td>{order && order.userEmail ? order.userEmail : ""}</td>
                        :
                        <></>
                }
                <td>
                    <IconButton onClick={toggleDisplay} aria-label="expand all" size="large" color="primary">
                        {!display ? <ExpandMoreIcon fontSize="large" /> : <ExpandLessIcon fontSize="large" />}
                    </IconButton>
                </td>
            </tr>
            {
                !display ? <></> :
                    <tr>
                        <td colSpan={panel === "EMPLOYEE" ? 6 : 5}>
                            <div className="flex-box-course">
                                <h2>Kursy</h2>
                                <table className="list-course">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nazwa kursu</th>
                                        <th>Cena kodu</th>
                                        <th>Liczba kodów</th>
                                        <th>Cena całkowita</th>
                                        <th>Rozwiń</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        {order && order.orderCourses ?
                                            order.orderCourses.map((course)=>(
                                                <CourseOrderCodes key={course.courseID} course={course} orderID={order.platformOrderID}/>
                                            )) : <></>}
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
            }
        </>
    );
}
