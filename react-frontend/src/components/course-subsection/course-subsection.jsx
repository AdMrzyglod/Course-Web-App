import './course-subsection.css'
import {useContext, useState} from "react";
import {getSubsectionFile} from "../../data/course_data";
import AuthContext from "../../contexts/AuthContext";



export default function CourseSubsection(props){

    let { getUserData } = useContext(AuthContext);

    const [fileUrl, setFileUrl] = useState(null);

    const handleOpenFile = (e) => {
        e.preventDefault();
        if(!props.fileExists || (!props.courseIsActive && getUserData() && getUserData().role && getUserData().role==="USER") || !getUserData()){
            return;
        }

        if(fileUrl==null){
            getSubsectionFile(props.subsectionID)
                .then((response) => {
                    if(response==null){
                        throw new Error()
                    }
                    return new Blob([response], { type: 'application/pdf' })
                })
                .then((data) => {
                    return URL.createObjectURL(data);
                })
                .then((fileDataURL)=>{
                    setFileUrl(fileDataURL);
                    window.open(fileDataURL, '_blank');
                })
                .catch((error) => {
                    console.error(error.message);
                });
        }
        else{
            window.open(fileUrl, '_blank');
        }
    };

    return (
        <>
            <div className="section-item" key={props.subsectionID}>
                <p>{props.name}</p>
                <button onClick={handleOpenFile} className={props.fileExists && (props.courseIsActive && getUserData() && getUserData().role && getUserData().role==="USER" ||
                    getUserData() && getUserData().role && getUserData().role!=="USER") ? "section-item-file" : "section-item-no-file"}>Otwórz materiał</button>
            </div>
        </>
    )
}

