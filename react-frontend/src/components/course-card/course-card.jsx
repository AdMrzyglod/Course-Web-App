import React, {useEffect, useState} from 'react';
import './course-card.css'
import courseIMG from "../../data_app/course_img.png"
import { Link, useLocation } from "react-router-dom";
import {getCourseImage} from "../../data/course_data";

export default function CourseCard(props){

    let url= useLocation().pathname;

    const [imageURL, setImageURL] = useState(null);


    useEffect(() => {

        getCourseImage(props.id)
            .then((response) => {
                if(response==null){
                    throw new Error()
                }
                return new Blob([response])
            })
            .then((data) => {
                return URL.createObjectURL(data);
            })
            .then((imageURL)=>{
                setImageURL(imageURL);
            })
            .catch((error) => {
                console.error(error.message);
            });

    }, [props.id]);

    return(
        <div className="card">
            <div className="content-box">
                <picture className="pictrue-card">
                    <img className="img-card" src={imageURL!=null ? imageURL : courseIMG} alt="course"/>
                </picture>
                <div className="content-details">
                    <h2>{props.name}</h2>
                    <div>
                        <div>
                            <p>Kategoria:</p>
                            <p>{props.category}</p>
                        </div>
                        <div>
                            <p>Data utworzenia:</p>
                            <p>{new Date(props.createDate).toLocaleString()}</p>
                        </div>
                        <div>
                            <p>Twórca:</p>
                            <p>{props.firstName + " " + props.lastName}</p>
                        </div>
                        <div>
                            <p>ID:</p>
                            <p>{props.id}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="price-box">
                <div className="product-price">
                    <p>Cena:</p>
                    <p>{props.price} PLN</p>
                </div>
                <div className='course-details'>
                    <Link to={`${url}/course-details/${props.id}`}>Szczegóły</Link>
                </div>
            </div>
        </div>
    )
}
