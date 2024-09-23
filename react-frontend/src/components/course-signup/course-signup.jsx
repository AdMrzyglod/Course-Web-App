import React, {useContext, useState} from 'react';
import './course-signup.css'
import {signup} from "../../data/user/authentication_data";
import AuthContext from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";


export default function CourseSignup(){

    let {userTokenCheck} = useContext(AuthContext);
    const navigate = useNavigate()

    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');



    const handleSubmit = async (e) => {
        e.preventDefault();

        if(firstname.trim()==="" || lastname.trim()==="" || username.trim()==="" || email.trim()==="" || password.trim()===""){
            return;
        }

        let data = {
            firstname: firstname.trim(),
            lastname: lastname.trim(),
            platformUsername: username.trim(),
            email: email.trim(),
            password: password.trim(),
        }

        console.log(data)

        await signup(data)
            .then(()=> {
                window.alert("Dodano użytkownika")
                setTimeout(()=>{
                    navigate("/")
                }, 5000)
            })
            .catch((error)=>console.error(error));

    };


    if(userTokenCheck()){
        navigate("/");
    }


    return (
        <div className="round-box">
            <form className="box-signup" onSubmit={handleSubmit}>
                <h2>Rejestracja</h2>
                <div className="input-signup">
                    <label>Imię:</label>
                    <input type="text" value={firstname} onChange={(e) => setFirstname(e.target.value)} required minLength={2}/>
                </div>
                <div className="input-signup">
                    <label>Nazwisko:</label>
                    <input type="text" value={lastname} onChange={(e) => setLastname(e.target.value)} required minLength={2}/>
                </div>
                <div className="input-signup">
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                </div>
                <div className="input-signup">
                    <label>Nazwa użytkownika:</label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required minLength={2}/>
                </div>
                <div className="input-signup">
                    <label>Hasło:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required minLength={6}/>
                </div>
                <button type="submit" className="signup-button">Zarejestruj się</button>
            </form>
        </div>
    )
}