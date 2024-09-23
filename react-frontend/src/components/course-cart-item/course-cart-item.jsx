import React, {useContext, useState} from 'react';
import './course-cart-item.css'
import courseIMG from "../../data_app/course_img.png"
import { Link, useLocation } from "react-router-dom";
import {CartContext} from "../../contexts/CartContext";

export default function CourseCartItem(props){

    let url= useLocation().pathname;

    const {cartCourseList, addOrUpdateCourseToCart, deleteCourseFromCart, clearCart, getCartTotal, findCourse} = useContext(CartContext)

    const [number, setNumber] = useState('0');




    const handleInputNumber = (event) => {

        const value = event.target.value;

        const numericValue = value.replace(/[^\d.]/g, '');

        if(!isNaN(parseInt(numericValue))){
            const courseData = findCourse(props.id);
            if(courseData!==undefined && parseInt(courseData.numberOfFreeCodes)-parseInt(numericValue)>=0){
                setNumber(parseInt(numericValue)+"");
            }
            else{
                setNumber(parseInt(courseData.numberOfFreeCodes)+"")
            }
        }
        else{
            setNumber("");
        }
    };


    const handleInputUpdate = (event) => {

        if(isNaN(parseInt(number)) || parseInt(number)<=0){
            return;
        }

        const courseData = findCourse(props.id);
        courseData.quantity = parseInt(number)+"";
        addOrUpdateCourseToCart(courseData);
    };


    return(
        <div className="cart-box">
            <div className="cart-item">
                <div className="content-item-box">
                    <picture className="pictrue-item">
                        <img className="img-item"
                             src={findCourse(props.id).imageURL != null ? findCourse(props.id).imageURL : courseIMG}
                             alt="course"/>
                    </picture>
                    <div className="content-item-details">
                        <h2>{findCourse(props.id).name}</h2>
                        <div>
                            <div>
                                <p>Kategoria:</p>
                                <p>{findCourse(props.id).category}</p>
                            </div>
                            <div>
                                <p>Data utworzenia:</p>
                                <p>{new Date(findCourse(props.id).createDate).toLocaleString()}</p>
                            </div>
                            <div>
                                <p>Twórca:</p>
                                <p>{findCourse(props.id).firstName + " " + findCourse(props.id).lastName}</p>
                            </div>
                            <div>
                                <p>Liczba wolnych kodów:</p>
                                <p>{findCourse(props.id).numberOfFreeCodes}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="cart-operation-box">
                    <div className="item-line"/>
                    <div className="product-item-box">
                        <div className="product-item-data">
                            <p>Cena:</p>
                            <p>{findCourse(props.id).price}</p>
                        </div>
                        <div className="product-item-data">
                            <p>Kody w koszyku:</p>
                            <p>{findCourse(props.id).quantity}</p>
                        </div>
                        <div className='course-item-details'>
                            <Link to={`/course-details/${props.id}`}>Szczegóły</Link>
                        </div>
                    </div>
                    <div className="item-line"/>
                    <div className="product-item-box">
                        <input
                            type="text"
                            value={number}
                            onChange={handleInputNumber}
                        />
                        <button type="button" onClick={handleInputUpdate}>Zmień</button>
                    </div>
                </div>
            </div>
            <button type="button" onClick={() => {
                deleteCourseFromCart(props.id)
            }}>Usuń
            </button>
        </div>
    )
}
