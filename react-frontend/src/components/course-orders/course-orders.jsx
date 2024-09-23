import './course-orders.css'
import React,{ useEffect,useState } from 'react';
import CourseOrderDetails from "../course-order-details/course-order-details";



export default function CourseOrders({getOrders, panel}){

    const [orderList, setOrderList] = useState([]);


    useEffect(() => {

        getOrders()
            .then(response => {
                setOrderList(response);
            })
            .catch(error => {
            console.log(error);
        });


    }, []);


    return (
        <div className="flex-box-order">
            <h2>Historia zamówień</h2>
            <table className="list-order">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data utworzenia</th>
                        <th>Liczba kodów</th>
                        <th>Cena całkowita</th>
                        {
                            panel === "EMPLOYEE" ?
                                <th>Użytkownik</th>
                                :
                                <></>
                        }
                        <th>Rozwiń</th>
                    </tr>
                </thead>
                <tbody>
                    {orderList.map((order) => (
                        <CourseOrderDetails key={order.platformOrderID} order={order} panel={panel}/>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

