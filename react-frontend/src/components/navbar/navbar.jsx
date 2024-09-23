import "./navbar.css"
import React, { useContext } from 'react';
import {Link, useNavigate} from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";


export default function Navbar(){


    let { getUserData, logoutUser } = useContext(AuthContext)

    const navigate = useNavigate()

    const handleLogout = (e) => {
        logoutUser(e);
        navigate("/");
    };


    return(
        <nav>
            <ul className="ul-navbar">
                <div>
                    <li><Link to="/">Home</Link></li>
                    <li className="dropdown">
                        <p className="dropbtn">Panel</p>
                        <div className="dropdown-content">
                            <Link to="/course-list">Lista</Link>
                            {getUserData() && getUserData().role === "USER"
                                ?
                                <>
                                    <Link to="/course-add">Dodaj kurs</Link>
                                    <Link to="/course-user-panel">Konto</Link>
                                </>
                                :
                                <></>
                            }
                            {getUserData() && (getUserData().role === "EMPLOYEE" || getUserData().role === "ADMIN" || getUserData().role === "SUPER_ADMIN")
                                ?
                                <>
                                    <Link to="/course-employee-panel">Panel pracownika</Link>
                                </>
                                :
                                <></>
                            }
                            {getUserData() && (getUserData().role === "ADMIN" || getUserData().role === "SUPER_ADMIN")
                                ?
                                <>
                                    <Link to="/course-admin-panel">Panel administratora</Link>
                                </>
                                :
                                <></>
                            }
                        </div>
                    </li>
                </div>
                <div>
                    {(getUserData() && getUserData().role === "USER") || !getUserData()
                        ? <li><Link to="/course-cart">Koszyk</Link></li>
                        :
                        <></>
                    }

                    {getUserData() ? (
                            <div className="login-user">
                                <li><p>{getUserData().sub}</p></li>
                                <li><button className="logout-button" onClick={handleLogout}>Logout</button></li>
                            </div>
                        ):
                        (
                            <div>
                                <li><Link to="/login">Login</Link></li>
                                <li><Link to="/signup">Signup</Link></li>
                            </div>
                        )}
                </div>
            </ul>
        </nav>
    )
}