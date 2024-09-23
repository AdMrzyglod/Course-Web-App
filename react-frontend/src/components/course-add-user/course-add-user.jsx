import React, {useContext, useState} from 'react';
import './course-add-user.css'
import AuthContext from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";


export default function CourseAddUser({addUserData, type}){

    let {userTokenCheck} = useContext(AuthContext);
    const navigate = useNavigate()

    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [activationDate, setActivationDate] = useState('');



    const handleSubmit = async (e) => {
        e.preventDefault();

        if(firstname.trim()==="" || lastname.trim()==="" || username.trim()==="" || email.trim()==="" || password.trim()==="" || activationDate.trim()===""){
            return;
        }

        let data = {
            firstname: firstname.trim(),
            lastname: lastname.trim(),
            platformUsername: username.trim(),
            email: email.trim(),
            password: password.trim(),
            activationDate: activationDate.trim(),
        }


        await addUserData(data)
            .then(()=> {
                window.alert("Dodano " + type.toLowerCase())
            })
            .catch((error)=>console.error(error));


    };


    return (
        <div className="round-box-user">
            <form className="box-add-user" onSubmit={handleSubmit}>
                <h2>Rejestracja {type.toLowerCase()}</h2>
                <div className="input-add-user">
                    <label>Imię:</label>
                    <input type="text" value={firstname} onChange={(e) => setFirstname(e.target.value)} required
                           minLength={2}/>
                </div>
                <div className="input-add-user">
                    <label>Nazwisko:</label>
                    <input type="text" value={lastname} onChange={(e) => setLastname(e.target.value)} required
                           minLength={2}/>
                </div>
                <div className="input-add-user">
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                </div>
                <div className="input-add-user">
                    <label>Nazwa użytkownika:</label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required
                           minLength={2}/>
                </div>
                <div className="input-add-user">
                    <label>Hasło:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required
                           minLength={6}/>
                </div>
                <div className="input-add-user">
                    <label>Data aktywacji konta:</label>
                    <input type="date" value={activationDate} onChange={(e) => setActivationDate(e.target.value)}
                           required/>
                </div>
                <button type="submit" className="add-user-button">Dodaj {type.toLowerCase()}</button>
            </form>
        </div>
    )
}