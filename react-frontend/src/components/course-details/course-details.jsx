import './course-details.css'
import courseIMG from "../../data_app/course_img.png"
import React, { useState, useContext,useEffect } from 'react';
import { Link, useParams, useLocation } from "react-router-dom";
import CourseSection from "../course-section/course-section";
import {
    activeCourseCodeData,
    codeCreate,
    getCourseActive,
    getCourseDetails,
    getCourseImage
} from "../../data/course_data";
import {CartContext} from "../../contexts/CartContext";
import AuthContext from "../../contexts/AuthContext";


export default function CourseDetails(){

    const {cartCourseList, addOrUpdateCourseToCart, deleteCourseFromCart, findCourse} = useContext(CartContext)
    let { getUserData } = useContext(AuthContext)

    let url=useLocation().pathname;

    const {id} = useParams();
    const [number, setNumber] = useState('0');
    const [codeInput, setCodeInput] = useState('');
    const [codeInputProblem,setCodeInputProblem] = useState('');

    const [details,setDetails] = useState({});
    const [imageURL, setImageURL] = useState(null);

    const [isInCart, setIsInCart] = useState(false);
    const [courseIsActive, setCourseIsActive] = useState(false);

    const [backButtonUrl, setBackButtonUrl] = useState('');

    const [numberOfCodesToCreate, setNumberOfCodesToCreate] = useState('0');



    useEffect(() => {

        getCourseDetails(id)
            .then(data=> {setDetails(data);})
            .catch(error => {
                console.log(error);
            });

    }, [id]);


    useEffect(() => {
        getCourseActive(id)
            .then(data=> setCourseIsActive(data))
            .catch(error => {
                console.log(error);
            });
    }, [id])


    useEffect(() => {
        setIsInCart(findCourse(id)!==undefined);
    }, [id, cartCourseList]);


    useEffect(() => {

        getCourseImage(id)
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

    }, [id]);


    useEffect(() => {
        backButtonUrlFunction();
    }, [url]);


    const handleInputNumber = (event) => {

        const value = event.target.value;

        const numericValue = value.replace(/[^\d.]/g, '');

        if(!isNaN(parseFloat(numericValue))){
            if(details && parseFloat(details.numberOfFreeCodes)-parseFloat(numericValue)>=0){
                setNumber(parseFloat(numericValue)+"");
            }
            else{
                setNumber(parseFloat(details.numberOfFreeCodes)+"")
            }
        }
        else{
            setNumber("");
        }
    };


    const handleInputCode = (event) => {

        const value = event.target.value;

        let codeValue = value.replace(/[^0-9a-f]/gi, '').toLowerCase();

        const indexList = [8,13,18,23]
        indexList.forEach((index) => {
            if(codeValue.length>=index+1){
                codeValue = codeValue.slice(0, index) + "-" + codeValue.slice(index);
            }
        })

        setCodeInput(codeValue);
    };

    const handleInputNumberOfCodesToCreate = (event) => {

        const value = event.target.value;

        const numericValue = value.replace(/[^\d.]/g, '');

        if(!isNaN(parseFloat(numericValue))){
            if(parseFloat(numericValue)>=0 && parseFloat(numericValue)<=100){
                setNumberOfCodesToCreate(parseFloat(numericValue)+"");
            }
            else if(parseFloat(numberOfCodesToCreate)<=100){
                setNumberOfCodesToCreate(parseFloat(numberOfCodesToCreate)+"");
            }
            else{
                setNumberOfCodesToCreate("")
            }
        }
        else{
            setNumberOfCodesToCreate("");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if(parseFloat(number)>0 && findCourse(id)===undefined){
            const cartData = {
                courseID: parseInt(details.courseID),
                name: details.name,
                imageURL: imageURL,
                createDate: details.createTimestamp,
                firstName: details.creator.firstname,
                lastName: details.creator.lastname,
                category: details.category.name,
                numberOfFreeCodes: details.numberOfFreeCodes,
                price: details.price,
                quantity: parseFloat(number),
            }
            addOrUpdateCourseToCart(cartData);
            setIsInCart(true);
        }
    };

    const handleActiveCodeSubmit = async (e) => {
        e.preventDefault();

        if(codeInput.length===36){
            const activeCodeInput = {
                courseID: parseInt(details.courseID),
                accessCode: codeInput,
            }
            await activeCourseCodeData(activeCodeInput)
                .then(async (response)=>{
                    if(response && response.status===201){
                        await getCourseActive(id)
                            .then(data=> setCourseIsActive(data))
                            .catch(error => {
                                console.log(error);
                            });
                    }
                    else{
                        setCodeInputProblem("Problem z kodem")
                    }
                })
                .catch((error) => {
                    console.error(error);
                })

        }
    };

    const handleCodeCreateSubmit = async (e) => {
        e.preventDefault();

        if(parseFloat(numberOfCodesToCreate)>0){
            const codeCreateData = {
                numberOfCodes: parseFloat(numberOfCodesToCreate),
            }

            await codeCreate(details.courseID, codeCreateData)
                .then(async ()=>{
                    await getCourseDetails(id)
                        .then(data=> {setDetails(data);})
                        .catch(error => {
                            console.log(error);
                        });
                })
                .catch((error) => {
                    console.error(error);
                })
        }
    };

    const backButtonUrlFunction = () => {
        if(url.includes("/course-user-panel")){
            setBackButtonUrl('/course-user-panel');
        }
        else if(url.includes("/course-list")){
            setBackButtonUrl('/course-list');
        }
        else if(url.includes("/course-employee-panel")){
            setBackButtonUrl('/course-employee-panel');
        }
        else{
            setBackButtonUrl('');
        }
    }



    return (
        <div className="box-course" key={details.courseID}>
            <Link to={backButtonUrl} className="backButton">Wstecz</Link>
            <div className="first-box">
                <div className="inform">
                    <h2>{details && details.name ? details.name : ""}</h2>
                    <picture className="pictrue-detail">
                        <img className="img-detail" src={imageURL!=null ? imageURL : courseIMG} alt="course"/>
                    </picture>
                </div>
                <div className="inform-details">
                    <p>
                        <span>Kategoria: </span> {details && details.category && details.category.name ? details.category.name : ""}
                    </p>
                    <p>
                        <span>Data utworzenia: </span> {details && details.createTimestamp ? new Date(details.createTimestamp).toLocaleString() : ""}
                    </p>
                    <p>
                        <span>ID: </span> {id ? id : ""}
                    </p>
                </div>
                <div className="inform-desc">
                    <p>Opis:</p>
                    <p>{details && details.description ? details.description : ""}</p>
                </div>
                <div className="inform-section">
                    <p>Dane kursu:</p>
                    {details && details.sections ?
                        details.sections.sort((a, b) => a.number - b.number).map((section) =>
                            <CourseSection key={section.sectionID} name={section.name}
                                           description={section.description} subsections={section.subsections}
                                           courseIsActive={courseIsActive || (getUserData() && getUserData().sub && details && details.creator && details.creator.email &&
                                               getUserData().sub === details.creator.email )}
                            />
                        )
                        : <></>}
                </div>
            </div>
            <div className="second-box">
                <div className="course-panel">
                    <div className="course-panel-box">
                        <p>Cena:</p>
                        <p>{details && details.price ? details.price : ""} PLN</p>
                    </div>
                    <form onSubmit={handleSubmit} className="course-panel-input">
                        <label>
                            Liczba
                            sztuk: {details && !isNaN(parseFloat(details.numberOfFreeCodes)) ? details.numberOfFreeCodes : ""}
                        </label>
                        {(getUserData() && getUserData().role && getUserData().role === "USER") || !getUserData()
                            ?
                                    !isInCart ?
                                    <>
                                        <input
                                            type="text"
                                            value={number}
                                            onChange={handleInputNumber}
                                        />
                                        <button type="submit">Kup</button>
                                    </> :
                                    <>
                                        <button type="button" onClick={() => {
                                            deleteCourseFromCart(id)
                                        }}>Usuń z koszyka
                                        </button>
                                    </>
                            :
                            <></>
                        }
                    </form>
                </div>
                {(getUserData() && getUserData().role && getUserData().role === "USER" && !courseIsActive &&
                    !(getUserData() && getUserData().sub && details && details.creator && details.creator.email &&
                    getUserData().sub === details.creator.email)) ?
                    <div className="course-panel">
                        <div className="course-panel-box">
                            <p>Aktywuj kod:</p>
                        </div>
                        <form onSubmit={handleActiveCodeSubmit} className="course-panel-code-input">
                            <input
                                type="text"
                                value={codeInput}
                                onChange={handleInputCode}
                                maxLength={36}
                            />
                            {codeInputProblem && codeInputProblem.length > 0 ?
                                <p>{codeInputProblem}</p>
                                :
                                <></>
                            }
                            <button type="submit">Aktywuj</button>
                        </form>
                    </div>
                    :
                    (getUserData() && getUserData().role && getUserData().role === "USER" ?
                        <div className="course-active-info">
                            <p>Status kursu:</p>
                            <p>Aktywny</p>
                        </div>
                        :
                            <></>
                    )
                }
                {
                    getUserData() && getUserData().role && getUserData().role !== "USER" && backButtonUrl.includes("/course-employee-panel") ?
                        <div className="course-panel">
                            <div className="course-panel-box">
                                <p>Dodawanie kodów:</p>
                            </div>
                            <form onSubmit={handleCodeCreateSubmit} className="course-panel-input">
                                <input
                                    type="text"
                                    value={numberOfCodesToCreate}
                                    onChange={handleInputNumberOfCodesToCreate}
                                />
                                <button type="submit">Dodaj</button>
                            </form>
                        </div>
                        :
                        <></>
                }
                <div className="user-box">
                    <div>
                        <p>Twórca:</p>
                        <p>{details && details.creator && details.creator.firstname && details.creator.lastname ?
                        details.creator.firstname + " " + details.creator.lastname : ""}</p>
                    </div>
                    <div>
                        <p>Email:</p>
                        <p>{details && details.creator && details.creator.email ?
                            details.creator.email : ""}</p>
                    </div>
                </div>
            </div>
        </div>
    )

}