import React, {useEffect, useState} from 'react';
import './course-user-panel.css'
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import CourseOrderHistory from "../course-order-history/course-order-history";
import CourseUserPaymentList from "../course-user-payment-list/course-user-payment-list";
import CourseUserCourseList from "../course-user-course-list/course-user-course-list";
import CourseUserCreateCourseList from "../course-user-create-course-list/course-user-create-course-list";
import CourseIndividualUserData from "../course-individual-user-data/course-individual-user-data";

export default function CourseUserPanel(){

    const [selectedOption, setSelectedOption] = useState("1");
    const [currentView, setCurrentView] = useState(null);

    useEffect(()=>{
        setCurrentView(<CourseUserCreateCourseList/>);
    },[])

    const handleOptionChange = (event, newOption) => {

        if(newOption===null){
            return;
        }

        if(newOption==="1"){
            setCurrentView(<CourseUserCreateCourseList/>)
        }
        if(newOption==="2"){
            setCurrentView(<CourseUserCourseList/>)
        }
        else if(newOption==="3"){
            setCurrentView(<CourseOrderHistory/>)
        }
        else if(newOption==="4"){
            setCurrentView(<CourseUserPaymentList/>)
        }
        else if(newOption==="5"){
            setCurrentView(<CourseIndividualUserData/>);
        }

        setSelectedOption(newOption);
    };



    return(
        <div className="history-box">
            <div className="options">
                <ToggleButtonGroup
                    orientation="vertical"
                    value={selectedOption}
                    exclusive
                    onChange={handleOptionChange}
                >
                    <ToggleButton value="1" aria-label="Option 1">
                        Stworzone kursy
                    </ToggleButton>
                    <ToggleButton value="2" aria-label="Option 1">
                        Aktywowane kursy
                    </ToggleButton>
                    <ToggleButton value="3" aria-label="Option 2">
                        Historia zamówień
                    </ToggleButton>
                    <ToggleButton value="4" aria-label="Option 3">
                        Wpłaty
                    </ToggleButton>
                    <ToggleButton value="5" aria-label="Option 4">
                        Dane konta
                    </ToggleButton>
                </ToggleButtonGroup>
            </div>
            {currentView ? currentView : <></>}
        </div>
    )
}