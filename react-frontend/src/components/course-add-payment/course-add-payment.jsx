import React, {useContext, useState} from 'react';
import './course-add-payment.css'
import AuthContext from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";
import {addPaymentData} from "../../data/payment_data";


export default function CourseAddPayment(){

    let {userTokenCheck} = useContext(AuthContext);
    const navigate = useNavigate()

    const [title, setTitle] = useState('');
    const [email, setEmail] = useState('');
    const [bankAccountNumber, setBankAccountNumber] = useState('');
    const [receiptDate, setReceiptDate] = useState('');
    const [amount, setAmount] = useState('');

    const [showInfo, setShowInfo] = useState(false);
    const [infoMessage, setInfoMessage] = useState(true);

    const handleInputBankAccountNumber = (event) => {

        const value = event.target.value;

        let bankValue = value.replace(/[^0-9]/gi, '');

        const indexList = [2,7,12,17,22,27]
        indexList.forEach((index) => {
            if(bankValue.length>=index+1){
                bankValue = bankValue.slice(0, index) + " " + bankValue.slice(index);
            }
        })

        setBankAccountNumber(bankValue);
    };


    const handleInputChangeAmount = (event) => {

        const value = event.target.value;

        const numericValue = value.replace(/[^\d.]/g, '');

        const [integerPart, decimalPart] = numericValue.split('.');

        let formattedDecimalPart = decimalPart || "00";
        if (formattedDecimalPart.length > 2) {
            formattedDecimalPart = formattedDecimalPart.slice(0, 2);
        }

        let formattedIntegerPart = integerPart || "0";
        if (formattedIntegerPart.length > 1 && formattedIntegerPart.startsWith("0")) {
            formattedIntegerPart = formattedIntegerPart.replace(/^0+/, "");
        }

        const formattedValue = `${formattedIntegerPart}.${formattedDecimalPart}`;

        setAmount(formattedValue);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if(title.trim()==="" || bankAccountNumber.trim()==="" || receiptDate.trim()==="" || amount.trim()==="" || email.trim()===""){
            return;
        }

        let data = {
            title: title.trim(),
            email: email.trim(),
            bankAccountNumber: bankAccountNumber.trim(),
            receiptDate: receiptDate.trim(),
            amount: amount.trim(),
        }


        await addPaymentData(data)
            .then((response)=> {
                if(response){
                    setShowInfo(true);
                    setInfoMessage(true);

                    setTitle('');
                    setEmail('');
                    setBankAccountNumber('');
                    setReceiptDate('');
                    setAmount('');

                    setTimeout(()=>{
                        setShowInfo(false);
                    },4000);
                }
                else{
                    setShowInfo(true);
                    setInfoMessage(false);
                    setTimeout(()=>{
                        setShowInfo(false);
                    },4000);
                }
            })
            .catch((error)=> {
                console.error(error);
                setShowInfo(true);
                setInfoMessage(false);
                setTimeout(()=>{
                    setShowInfo(false);
                },4000);
            });

    };


    return (
        <div className="round-box-payment">
            <form className="box-payment" onSubmit={handleSubmit}>
                <h2>Dodaj płatność</h2>
                <div className="input-payment">
                    <label>Tytuł:</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required minLength={2}/>
                </div>
                <div className="input-payment">
                    <label>Email użytkownika:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                </div>
                <div className="input-payment">
                    <label>Numer konta bankowego:</label>
                    <input type="text" value={bankAccountNumber} onChange={handleInputBankAccountNumber} minLength={32}
                           maxLength={32} required/>
                </div>
                <div className="input-payment">
                    <label>Data otrzymania:</label>
                    <input type="date" value={receiptDate} onChange={(e) => setReceiptDate(e.target.value)} required/>
                </div>
                <div className="input-payment">
                    <label>Kwota:</label>
                    <input type="text" value={amount} onChange={handleInputChangeAmount} required/>
                </div>
                <button type="submit" className="payment-button">Dodaj wpłatę</button>
            </form>
            <p className={`payment-info ${!showInfo ? "hide-info" : ""}`}>{infoMessage ? "Dodano wpłatę" : "Nie dodano wpłaty"}</p>
        </div>
    )
}