import './course-user-data.css'

import React, { useState, useEffect } from 'react';



export default function CourseUserData({getUserAccountData, panel}){


    const [userAccountData, setUserAccountData] = useState(null);




    useEffect(()=>{
        getUserAccountData()
            .then(response => setUserAccountData(response))
            .catch(error => {
                console.error(error);
            });
    },[])


    return (
        <div className="user-data">
            <div>
                <p>Nazwa użytkownika:</p>
                <p>{userAccountData ? userAccountData.platformUsername : ""}</p>
            </div>
            <div>
                <p>Imię:</p>
                <p>{userAccountData ? userAccountData.firstname : ""}</p>
            </div>
            <div>
                <p>Nazwisko:</p>
                <p>{userAccountData ? userAccountData.lastname : ""}</p>
            </div>
            <div>
                <p>Email:</p>
                <p>{userAccountData ? userAccountData.email : ""}</p>
            </div>
            {
                panel==="USER" ?
                    <>
                        <div>
                            <p>Stan konta:</p>
                            <p>{userAccountData ? userAccountData.money : ""} PLN</p>
                        </div>
                    </>
                    :
                    <></>
            }
            {
                panel==="EMPLOYEE" || panel==="ADMIN" ?
                    <>
                        <div>
                            <p>Data aktywacji:</p>
                            <p>{userAccountData && userAccountData.activationDate}</p>
                        </div>
                        <div>
                            <p>Status:</p>
                            <p>{userAccountData && userAccountData.accountActive ? (userAccountData.accountActive ? "Aktywny" : "Nieaktywny"): ""}</p>
                        </div>
                    </>
                    :
                    <></>
            }

        </div>
    )
}





