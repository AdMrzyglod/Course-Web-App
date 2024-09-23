import React, {useContext, useEffect, useState} from 'react';
import './course-admin-panel.css'
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import CourseAddEmployee from "../course-add-employee/course-add-employee";
import CourseUserList from "../course-user-list/course-user-list";
import CourseAddAdmin from "../course-add-admin/course-add-admin";
import AuthContext from "../../contexts/AuthContext";
import CourseAdminData from "../course-admin-data/course-admin-data";

export default function CourseAdminPanel(){

    let { getUserData } = useContext(AuthContext);

    const [selectedOption, setSelectedOption] = useState("1");
    const [currentView, setCurrentView] = useState(null);

    const [optionList, setOptionList] = useState([
        <ToggleButton value="1" aria-label="Option 1">
            Użytkownicy
        </ToggleButton>,
        <ToggleButton value="2" aria-label="Option 2">
            Dodaj pracownika
        </ToggleButton>
    ]);

    useEffect(()=>{
        setCurrentView(<CourseUserList/>);

        let options = [...optionList];

        if(getUserData() && getUserData().role==="SUPER_ADMIN"){
            options.push(
                <ToggleButton value="3" aria-label="Option 3">
                    Dodaj administratora
                </ToggleButton>
            )
        }

        options.push(
            <ToggleButton value="4" aria-label="Option 4">
                Dane konta
            </ToggleButton>
        )

        setOptionList(options);

    },[])

    const handleOptionChange = (event, newOption) => {

        if(newOption===null){
            return;
        }

        if(newOption==="1"){
            setCurrentView(<CourseUserList/>)
        }
        if(newOption==="2"){
            setCurrentView(<CourseAddEmployee/>)
        }
        if(newOption==="3"){
            setCurrentView(<CourseAddAdmin/>)
        }
        if(newOption==="4"){
            setCurrentView(<CourseAdminData/>)
        }


        setSelectedOption(newOption);
    };



    return(
        <div className="admin-panel-box">
            <div className="admin-options">
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