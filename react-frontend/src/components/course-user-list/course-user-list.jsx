import './course-user-list.css'
import React, {useContext, useEffect, useState} from 'react';
import CourseUserListDetails from "../course-user-list-details/course-user-list-details";
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import {getPlatformUsersByRole} from "../../data/user/user_data";
import AuthContext from "../../contexts/AuthContext";


export default function CourseUserList(){

    let { getUserData } = useContext(AuthContext);

    const [userList, setUserList] = useState([]);
    const [selectedRole, setSelectedRole] = useState("USER");

    const [optionList, setOptionList] = useState([
        <ToggleButton value="USER" aria-label="Option 1">
            Indywidualni
        </ToggleButton>,
        <ToggleButton value="EMPLOYEE" aria-label="Option 1">
            Pracownicy
        </ToggleButton>
    ]);

    useEffect(() => {
        if(getUserData() && getUserData().role==="SUPER_ADMIN"){
            setOptionList([...optionList,
                <ToggleButton value="ADMIN" aria-label="Option 2">
                    Administratorzy
                </ToggleButton>
            ])
        }
    }, []);

    const createRoleData = (role) => {
        return {role: role};
    }

    const handleOptionChange = (event, newOption) => {

        if(newOption===null){
            return;
        }
        setSelectedRole(newOption);
    };


    useEffect(() => {

        getPlatformUsersByRole(createRoleData(selectedRole))
            .then(response => {
                setUserList(response);
            })
            .catch(error => {
            console.log(error);
        });


    }, [selectedRole]);


    return (
        <div className="flex-box-user-list">
            <h2>Użytkownicy</h2>
            <div className="options-user-list">
                <ToggleButtonGroup
                    orientation="horizontal"
                    value={selectedRole}
                    exclusive
                    onChange={handleOptionChange}
                >
                    {optionList}
                </ToggleButtonGroup>
            </div>
            <table className="list-user">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Rola</th>
                        <th>Rozwiń</th>
                    </tr>
                </thead>
                <tbody>
                    {userList.map((user) => (
                        <CourseUserListDetails key={user.platformUserID} user={user}/>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

