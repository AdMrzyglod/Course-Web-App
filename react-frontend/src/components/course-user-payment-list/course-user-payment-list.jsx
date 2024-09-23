import './course-user-payment-list.css'
import React,{ useEffect,useState } from 'react';
import {getUserPayments} from "../../data/payment_data";


export default function CourseUserPaymentList(){

    const [paymentList, setPaymentList] = useState([]);



    useEffect(() => {

        getUserPayments()
            .then(response => {
                setPaymentList(response);
            })
            .catch(error => {
                console.log(error);
            });


    }, []);

    return (
        <div className="flex-box-user-payment-list">
            <h2>Wpłaty</h2>
            <p>Konto wpłat: numer konta , Tytuł z emailem konta.</p>
            <table className="list-user-payment">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tytuł</th>
                        <th>Data utworzenia</th>
                        <th>Data otrzymania</th>
                        <th>Numer konta bankowego</th>
                        <th>Kwota</th>
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
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

