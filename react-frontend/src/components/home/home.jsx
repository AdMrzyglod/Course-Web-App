import React from 'react';
import './home.css'
import { Link } from 'react-router-dom';


export default function Home(){

    return(
        <>
            <div className="box">
                <div className="intro">
                    <div className="company-name">
                        <p>CourseWeb</p>
                    </div>
                    <div className="company-ad">
                        <p>Strona z kursami online!</p>
                    </div>
                    <div className="company-button">
                        <Link to='/course-list'>Kursy</Link>
                    </div>
                </div>
                <div className="info">
                    <div className="info-contact">
                        <h2>Dane kontaktowe</h2>
                        <div>
                            <p>Telefon:</p>
                            <p>111 111 111</p>
                        </div>
                        <div>
                            <p>Adres:</p>
                            <p>City Street</p>
                        </div>
                        <div>
                            <p>Email:</p>
                            <p>example@example.com</p>
                        </div>
                    </div>
                    <div className="info-description">
                        <h2>CourseWeb</h2>
                        <div>
                            <p>Opis:</p>
                            <p>Description about company.</p>
                        </div>
                        <div>
                            <p>Godziny kontaktowe:</p>
                            <p>poniedziałek-piątek: 8-20</p>
                            <p>sobota-niedziela: 10-18</p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}