import './course-course-list.css'
import React,{ useEffect,useState } from 'react';
import {getCategories} from "../../data/course_data";
import CourseFilters from "../course-filters/course-filters";
import CourseCard from "../course-card/course-card";


export default function CourseCourseList({getCoursesData}){

    const [listItems, setListItems] = useState([]);
    const [filterList, setFilterList] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [pattern, setPattern] = useState('');
    const [categories, setCategories] = useState([]);


    const handleCategoryChange = (event) => {
        setSelectedCategories(event.target.value);
    };


    useEffect(()=>{

        if(selectedCategories.length===0 && pattern===""){
            setFilterList(listItems);
            return;
        }

        let filterCourses=listItems.filter((course)=>{
            return selectedCategories.includes(course.category.name) || selectedCategories.length===0;
        })

        let filterCoursesPattern = filterCourses.filter((offer)=>{
            let patternParts = pattern.split(" ").filter((part)=> {return part!==""});
            return patternParts.some((part) => offer.name.toLowerCase().includes(part.toLowerCase())) || pattern === "";
        });

        setFilterList(filterCoursesPattern);

    },[selectedCategories,pattern])


    useEffect(()=>{

        getCategories()
            .then(response => setCategories(response))
            .catch(error => {
                console.error(error.message);
            });

    },[])

    useEffect(() => {

        getCoursesData()
            .then(response => {
                setListItems(response);
                setFilterList(response);
            })
            .catch(error => {
            console.log(error);
        });


    }, []);


    return(
        <div className="flex-box">
            <CourseFilters pattern={pattern} setPattern={setPattern} selectedCategories={selectedCategories} handleCategoryChange={handleCategoryChange} categories={categories} />
            <div className="list">
                {filterList.map((course) =>
                    <CourseCard key={course.courseID} id={course.courseID} name={course.name}
                                 createDate={course.createTimestamp}
                                 firstName={course.creator.firstname}
                                 lastName={course.creator.lastname}
                                 category={course.category.name}  price={course.price}
                    />
                )}
            </div>
        </div>
    )
}

