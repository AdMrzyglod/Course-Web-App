import React, {useContext, useEffect, useState} from 'react';
import './course-employee-panel.css'
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import AuthContext from "../../contexts/AuthContext";
import CourseAddPayment from "../course-add-payment/course-add-payment";
import CoursePaymentList from "../course-payment-list/course-payment-list";
import CourseList from "../course-list/course-list";
import CourseAllOrders from "../course-all-orders/course-all-orders";
import CourseEmployeeData from "../course-employee-data/course-employee-data";

export default function CourseEmployeePanel(){

    let { getUserData } = useContext(AuthContext);

    const [selectedOption, setSelectedOption] = useState("1");
    const [currentView, setCurrentView] = useState(null);

    const [optionList, setOptionList] = useState([
        <ToggleButton value="1" aria-label="Option 1">
            Kursy
        </ToggleButton>,
        <ToggleButton value="2" aria-label="Option 1">
            Wszystkie zamówienia
        </ToggleButton>,
        <ToggleButton value="3" aria-label="Option 1">
            Lista wpłat
        </ToggleButton>,
        <ToggleButton value="4" aria-label="Option 2">
            Dodaj wpłatę
        </ToggleButton>
    ]);

    useEffect(()=>{
        setCurrentView(<CourseList/>);

        if(getUserData() && getUserData().role==="EMPLOYEE"){
            setOptionList([...optionList,
                <ToggleButton value="5" aria-label="Option 5">
                    Dane konta
                </ToggleButton>
            ])
        }
    },[])

    const handleOptionChange = (event, newOption) => {

        if(newOption===null){
            return;
        }

        if(newOption==="1"){
            setCurrentView(<CourseList/>)
        }
        if(newOption==="2"){
            setCurrentView(<CourseAllOrders/>)
        }
        if(newOption==="3"){
            setCurrentView(<CoursePaymentList/>)
        }
        if(newOption==="4"){
            setCurrentView(<CourseAddPayment/>)
        }
        if(newOption==="5"){
            setCurrentView(<CourseEmployeeData/>)
        }


        setSelectedOption(newOption);
    };



    return(
        <div className="employee-panel-box">
            <div className="employee-options">
                <ToggleButtonGroup
                    orientation="vertical"
                    value={selectedOption}
                    exclusive
                    onChange={handleOptionChange}
                >
                    {optionList}
                </ToggleButtonGroup>
            </div>
            {currentView ? currentView : <></>}
        </div>
    )
}