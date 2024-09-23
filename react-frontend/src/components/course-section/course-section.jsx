import './course-section.css'
import React, {useState} from "react";
import CourseSubsection from "../course-subsection/course-subsection";



export default function CourseSection(props){

    const [display,setDisplay] = useState(false);

    const toggleDisplay = () => {
        setDisplay(!display);
    };

    return (
        <>
            <div className="section-box" onClick={toggleDisplay}>
                <p>{props.name ? props.name : ""}</p>
            </div>
            {
                    !display ? <></> :
                        <div className="section-data">
                            <div className="section-item section-description">
                                <p>Opis:</p>
                                <p>{props.description ? props.description : ""}</p>
                            </div>
                            {props && props.subsections ? props.subsections.sort((a, b) => a.number - b.number).map((data) =>
                                <CourseSubsection key={data.subsectionID} name={data.name}
                                                  subsectionID={data.subsectionID}
                                                  fileExists={data.fileExists}
                                                  courseIsActive={props.courseIsActive}
                                />
                            ) : <></>}
                        </div>
            }
        </>
    )
}

