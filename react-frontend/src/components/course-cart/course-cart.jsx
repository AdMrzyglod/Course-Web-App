import './course-cart.css'
import React, {useContext} from 'react';
import CourseCartItem from "../course-cart-item/course-cart-item";
import {CartContext} from "../../contexts/CartContext";
import {addOrderData} from "../../data/order_data";


export default function CourseCart(){

    const {cartCourseList, addOrUpdateCourseToCart, deleteCourseFromCart, clearCart, getCartTotal} = useContext(CartContext)



    const handleSubmit = async (e) => {
        e.preventDefault()

        const order = {
            orderDetailsCreateDTOList: cartCourseList.map((orderDetails)=>{
                return {
                    courseID: orderDetails.courseID,
                    quantity: parseInt(orderDetails.quantity)
                }
            })
        }

        console.log(order);
        const isAdd = await addOrderData(order);

        if(isAdd){
            window.alert("Kupiono")
            clearCart()
        }
        else{
            window.alert("Problem z zakupem")
        }
    }


    return (
        <div className="flex-box-cart">
            <form className="box-cart" onSubmit={handleSubmit}>
                <h2>Koszyk</h2>
                {cartCourseList.length === 0 ?
                    <p className="empty-cart">
                        Koszyk pusty
                    </p>
                    :
                    <div className="cart-list">
                        {cartCourseList.map((course) =>
                            <CourseCartItem key={course.courseID} id={course.courseID}/>
                        )}
                    </div>}
                <p className="total-price-box">Cena całkowita: {getCartTotal()} PLN</p>
                <button type="submit" className="buy-button">Kup</button>
            </form>
        </div>
    )
}

