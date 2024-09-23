import './course-payment-list.css'
import React,{ useEffect,useState } from 'react';
import {deletePaymentData, getPayments} from "../../data/payment_data";


export default function CoursePaymentList(){

    const [paymentList, setPaymentList] = useState([]);



    useEffect(() => {

        getPayments()
            .then(response => {
                setPaymentList(response);
            })
            .catch(error => {
                console.log(error);
            });

    }, []);

    const handlePaymentDelete = async (event, payment) => {
        event.preventDefault();

        if(payment && payment.paymentID){
            await deletePaymentData(payment.paymentID)
                .then((data)=>{
                    getPayments()
                        .then(response => {
                            setPaymentList(response);
                        })
                        .catch(error => {
                            console.log(error);
                        });

                })
                .catch(error => {
                    console.log(error);
                });
        }

    };


    return (
        <div className="flex-box-payment-list">
            <h2>Wpłaty</h2>
            <table className="list-payment">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tytuł</th>
                        <th>Data utworzenia</th>
                        <th>Data otrzymania</th>
                        <th>Numer konta bankowego</th>
                        <th>Kwota</th>
                        <th>Użytkownik</th>
                        <th>Usuń wpłatę</th>
                    </tr>
                </thead>
                <tbody>
                    {paymentList.map((payment) => (
                        <tr>
                            <td>{payment && payment.paymentID ? payment.paymentID : ""}</td>
                            <td>{payment && payment.title ? payment.title : ""}</td>
                            <td>{payment && payment.createTimestamp ? new Date(payment.createTimestamp).toLocaleString() : ""}</td>
                            <td>{payment && payment.receiptDate ? new Date(payment.receiptDate).toLocaleDateString() : ""}</td>
                            <td>{payment && payment.bankAccountNumber ? payment.bankAccountNumber : ""}</td>
                            <td>{payment && payment.amount ? payment.amount : ""} PLN</td>
                            <td>{payment && payment.userEmail ? payment.userEmail : ""}</td>
                            <td><button onClick={(e) => handlePaymentDelete(e, payment)}
                                        className={"payment-delete-button"}>Usuń</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

