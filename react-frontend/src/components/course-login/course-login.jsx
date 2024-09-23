import React, {useContext, useState} from 'react';
import './course-login.css'
import {login} from "../../data/user/authentication_data";
import AuthContext from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";

export default function CourseLogin(){

    let {userTokenCheck, loginUser} = useContext(AuthContext);
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [error, setError] = useState(false);


    const handleSubmit = async (e) => {
        e.preventDefault();

        if(email==="" || password===""){
            return;
        }

        let data = {
            email: email,
            password: password
        }

        await login(data)
            .then((data)=>{
                loginUser(data);
            })
            .catch((error)=> {
                console.error(error);
                setError(true);
            });
    };


    if(userTokenCheck()){
        navigate("/");
    }


    return (
        <div className="round-box">
            <form className="box-login" onSubmit={handleSubmit}>
                <h2>Logowanie</h2>
                <div className="input-login">
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                </div>
                <div className="input-login">
                    <label>Hasło:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required minLength={6}/>
                </div>
                {!error ? <></> : <p className="error-message">Niepoprawne dane</p>}
                <button type="submit" className="login-button">Zaloguj się</button>
            </form>
        </div>
    )
}