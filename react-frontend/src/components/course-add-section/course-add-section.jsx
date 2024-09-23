import './course-add-section.css'
import React, {useState} from 'react';



export default function CourseAddSection({id,handleSectionDelete,sections,setSections}){


    const [subsectionName, setSubsectionName] = useState('');
    const [file, setFile] = useState(null);

    const handleNameChange = (event) => {
        let update = [...sections];
        update[id].name = event.target.value;
        setSections(update)
    }

    const handleDescriptionChange = (event) => {
        let update = [...sections];
        update[id].description = event.target.value;
        setSections(update)
    }

    const handleSubsectionAdd = (event) =>{
        event.preventDefault();
        if(subsectionName.length===0 || file==null || file.type!=='application/pdf'){
            return;
        }
        let update = [...sections];
        update[id].subsections.push({name:subsectionName, fileData:file})
        setSubsectionName("")
        setFile(null)
        setSections(update)
    }

    const handleSubsectionDelete = (event,index) =>{
        let update = [...sections];
        update[id].subsections.splice(index,1)
        setSections(update)
    }

    return (
        <div className="section-add-box" key={id}>
            <div className="input-section-box">
                <label>Nazwa: {id + 1}</label>
                <input type="text" value={sections[id].name} onChange={handleNameChange} required/>
            </div>
            <div className="input-section-box">
                <label>Opis: {id + 1}</label>
                <input type="text" value={sections[id].description} onChange={handleDescriptionChange} required/>
            </div>
            {sections[id].subsections.map((value, index) =>
                <>
                    <div className="subsection-item" key={index}>
                        <p>{value.name}</p>
                        <p>{value.fileData.name}</p>
                    </div>
                    <button type="button" className="remove-subsection"
                            onClick={(event) => handleSubsectionDelete(event, index)}>Usuń
                    </button>
                </>
            )}
            <div className="input-section-box">
                <label>Nazwa pliku:</label>
                <input type="text" value={subsectionName} onChange={(e) => setSubsectionName(e.target.value)}/>
                <label>Plik:</label>
                <input type="file" onChange={(e) => setFile(e.target.files[0])}/>
                <button type="button" className="remove-section"
                        onClick={(event) => handleSubsectionAdd(event)}>Dodaj podsekcje
                </button>
            </div>
            <button type="button" className="remove-section" onClick={(event) => handleSectionDelete(event, id)}>Usuń
                sekcje
            </button>
        </div>
    )
}