import React, {useContext, useState} from 'react';
import './course-user-list-details.css'
import { useLocation } from "react-router-dom";
import IconButton from '@mui/material/IconButton';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import {getPlatformUsersByRoleAndPlatformUserID} from "../../data/user/user_data";
import AuthContext from "../../contexts/AuthContext";
import {updateEmployeeState} from "../../data/user/employee_data";
import {updateAdminState} from "../../data/user/admin_data";

export default function CourseUserListDetails({user}){

    let { getUserData } = useContext(AuthContext);

    let url= useLocation().pathname;

    const [userData, setUserData] = useState(null);
    const [display,setDisplay] = useState(false);

    const toggleDisplay = async () => {
        if(!display && user && user.platformUserID && user.role){
            await getPlatformUsersByRoleAndPlatformUserID(user.role, user.platformUserID)
                .then(response => {
                    setUserData(response);
                })
                .catch(error => {
                    console.log(error);
                });
        }
        else{
            setUserData(null);
        }
        setDisplay(!display);
    };

    const updateState = async () => {

        if(userData && user && user.role!=="USER" && userData){
            let dataUpdated =
                {
                    platformUserID: user.platformUserID,
                    accountActive: !userData.accountActive
                }
            if(user.role==="EMPLOYEE"){
                await updateEmployeeState(dataUpdated)
                    .then(async () => {
                        await getPlatformUsersByRoleAndPlatformUserID(user.role, user.platformUserID)
                            .then(response => {
                                setUserData(response);
                            })
                            .catch(error => {
                                console.log(error);
                            });
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
            else if(user.role==="ADMIN" && getUserData() && getUserData().role==="SUPER_ADMIN"){
                await updateAdminState(dataUpdated)
                    .then(async () => {
                        await getPlatformUsersByRoleAndPlatformUserID(user.role, user.platformUserID)
                            .then(response => {
                                setUserData(response);
                            })
                            .catch(error => {
                                console.log(error);
                            });
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        }
    };



    return (
        <>
            <tr>
                <td>{user && user.platformUserID ? user.platformUserID : ""}</td>
                <td>{user && user.email ? user.email : ""}</td>
                <td>{user && user.role ? user.role : ""}</td>
                <td>
                    <IconButton onClick={toggleDisplay} aria-label="expand all" size="large" color="primary">
                        {!display ? <ExpandMoreIcon fontSize="large" /> : <ExpandLessIcon fontSize="large" />}
                    </IconButton>
                </td>
            </tr>
            {
                !display ? <></> :
                    <tr>
                        <td colSpan={4}>
                            <div className="flex-box-user-list-details">
                                <h2>Dane użytkownika</h2>
                                <div className="user-details-data">
                                    <div>
                                        <p>Email:</p>
                                        <p>{userData ? userData.email : ""}</p>
                                    </div>
                                    <div>
                                        <p>Nazwa użytkownika:</p>
                                        <p>{userData ? userData.platformUsername : ""}</p>
                                    </div>
                                    <div>
                                        <p>Imię:</p>
                                        <p>{userData ? userData.firstname : ""}</p>
                                    </div>
                                    <div>
                                        <p>Nazwisko:</p>
                                        <p>{userData ? userData.lastname : ""}</p>
                                    </div>
                                    {user && user.role==="USER" ?
                                        <div>
                                            <p>Stan konta:</p>
                                            <p>{userData && userData.money} PLN</p>
                                        </div>
                                        :
                                        <></>
                                    }
                                    {user && ( user.role === "EMPLOYEE" || user.role === "ADMIN" ) ?
                                        <>
                                            <div>
                                                <p>Data aktywacji:</p>
                                                <p>{userData && userData.activationDate}</p>
                                            </div>
                                            <div>
                                                <p>Status:</p>
                                                <p>{userData ? (userData.accountActive ? "Aktywny" : "Nieaktywny"): ""}</p>
                                            </div>
                                        </>
                                        :
                                        <></>
                                    }
                                </div>
                                {
                                    user && (user.role==="EMPLOYEE" || user.role==="ADMIN") ?
                                        <button type="button" onClick={updateState} className="update-state">Zmień status</button>
                                        :
                                        <></>
                                }
                            </div>
                        </td>
                    </tr>
            }
        </>
    );
}
