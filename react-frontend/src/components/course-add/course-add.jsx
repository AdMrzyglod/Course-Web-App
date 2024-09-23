import './course-add.css'

import React, { useState, useEffect } from 'react';
import {addCourseData, getCategories} from "../../data/course_data";
import CourseAddSection from "../course-add-section/course-add-section";



export default function CourseAdd(){

    const [categories, setCategories] = useState([]);

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('');
    const [price, setPrice] = useState('');
    const [image, setImage] = useState(null);
    const [sections, setSections] = useState([]);



    useEffect(()=>{
        getCategories()
            .then(response => setCategories(response))
            .catch(error => {
                console.error(error.message);
            });
    },[])


    const handleInputChangeMoney = (event) => {

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

        setPrice(formattedValue);
    };

    const handleSectionAdd = (event) => {
        let copySections = [...sections,{name:"",description: "", subsections:[]}]
        setSections(copySections)
    }

    const handleSectionDelete = (event,index) => {
        event.preventDefault();
        const updatedSections = [...sections];
        updatedSections.splice(index, 1);
        setSections(updatedSections);
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        sections.forEach((section, index)=>{
            section.number = index+1;
            section.subsections.forEach((subsection, nextNumber)=>{
                subsection.number = nextNumber+1;
            })
        })

        const sectionNoFiles = sections.map((section)=> { return {
            name: section.name,
            description: section.description,
            number: section.number,
            subsections: section.subsections.map((subsection)=>{return {
                name: subsection.name,
                number: subsection.number,
            }})
        }})

        const courseData = {
            name: name,
            description: description,
            categoryID: categories.find((data)=> data.name === category).categoryID,
            price: parseFloat(price),
            sections: sectionNoFiles
        };

        await addCourseData(courseData, sections, image);

    }


    return (
        <div className="round-box">
            <form className="box-add" onSubmit={handleSubmit}>
                <h2>Dodaj kurs</h2>
                <div className="input-box">
                    <label>Nazwa:</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required/>
                </div>
                <div className="flex-container">
                    <div className="input-box content">
                        <label>Kategoria:</label>
                        <select className="select-add" value={category} onChange={(e) => setCategory(e.target.value)}
                                required>
                            <option></option>
                            {categories.map((category, index) => {
                                return (<option key={index}>{category.name}</option>)
                            })}
                        </select>
                    </div>
                </div>
                <div className="input-box">
                    <label>Opis:</label>
                    <textarea className="areabox" value={description} onChange={(e) => setDescription(e.target.value)}
                              required></textarea>
                </div>
                <div className="input-box">
                    <label>Cena:</label>
                    <input type="text" value={price} onChange={handleInputChangeMoney} required/>
                </div>
                <div className="input-box content content-min">
                    <label>Zdjęcie:</label>
                    <input type="file" onChange={(e) => setImage(e.target.files[0])}/>
                </div>
                <div className="input-box">
                    <label>Sekcje:</label>
                    {
                        sections.map((data,index)=>
                            <CourseAddSection key={index} id={index}
                                              handleSectionDelete={handleSectionDelete}
                            sections={sections} setSections={setSections}/>)
                    }
                    <button type="button" className="add-section" onClick={handleSectionAdd}>Dodaj nową sekcje</button>
                </div>
                <button type="submit" className="add-button">Dodaj</button>
            </form>
        </div>
    )
}





