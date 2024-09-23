import React, {useState} from 'react';
import './course-order-codes.css'
import { useLocation } from "react-router-dom";
import IconButton from '@mui/material/IconButton';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import {getOrderHistoryCourseCodes} from "../../data/order_data";

export default function CourseOrderCodes({course, orderID}){

    let url= useLocation().pathname;

    const [display,setDisplay] = useState(false);
    const [codes, setCodes] = useState([]);

    const toggleDisplay = () => {
        if(!display && orderID !== undefined && course && course.courseID !== undefined){
            getOrderHistoryCourseCodes(orderID, course.courseID)
                .then(response => {
                    setCodes(response);
                })
                .catch(error => {
                    console.log(error);
                });
        }
        else{
            setCodes([]);
        }
        setDisplay(!display);
    };




    return (
        <>
            <tr>
                <td>{course && course.courseID ? course.courseID : ""}</td>
                <td>{course && course.name ? course.name : ""}</td>
                <td>{course && course.coursePrice ? course.coursePrice : ""} PLN</td>
                <td>{course && course.numberOfCourseCodes ? course.numberOfCourseCodes : ""}</td>
                <td>{course && course.courseCodesPrice ? course.courseCodesPrice : ""} PLN</td>
                <td>
                    <IconButton onClick={toggleDisplay} aria-label="expand all" size="large" color="primary">
                        {!display ? <ExpandMoreIcon fontSize="large"/> : <ExpandLessIcon fontSize="large"/>}
                    </IconButton>
                </td>
            </tr>
            {
                !display ? <></> :
                    <tr>
                    <td colSpan={6}>
                            <div className="flex-box-code">
                                <h2>Kody</h2>
                                <table className="list-code">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Kod dostępu</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {codes ? codes.map((code)=>(
                                            <tr key={code.courseCodeID}>
                                                <td>{code && code.courseCodeID ? code.courseCodeID : ""}</td>
                                                <td>{code && code.accessCode ? code.accessCode : ""}</td>
                                                <td>{code.activated ? "AKTYWOWANO" : "NIE AKTYWOWANO"}</td>
                                            </tr>
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
