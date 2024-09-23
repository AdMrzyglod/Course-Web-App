package com.application.mainapp.service;


import com.application.mainapp.model.FileData;
import com.application.mainapp.model.Subsection;
import com.application.mainapp.repository.FileDataRepository;
import com.application.mainapp.repository.SubsectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileDataService {

    private final SubsectionRepository subsectionRepository;
    private final FileDataRepository fileDataRepository;

    @Autowired
    public FileDataService(SubsectionRepository subsectionRepository, FileDataRepository fileDataRepository) {
        this.subsectionRepository = subsectionRepository;
        this.fileDataRepository = fileDataRepository;
    }

    @Transactional
    public FileData getFileData(Long subsectionID) {
        Optional<FileData> fileDataOptional = fileDataRepository.findFileDataBySubsection_SubsectionID(subsectionID);

        if(fileDataOptional.isPresent()) {
            return fileDataOptional.get();
        }
        throw new RuntimeException("No file found for the subsection id:" + subsectionID);
    }

    @Transactional
    public void saveFileData(FileData fileData, Long subsectionID) throws IOException {
        Optional<Subsection> subsectionOptional = this.subsectionRepository.findById(subsectionID);
        if(subsectionOptional.isEmpty()){
            throw new RuntimeException("Subsection not exists");
        }
        Subsection subsection = subsectionOptional.get();

        fileData.setSubsection(subsection);

        fileDataRepository.save(fileData);
    }


    @Transactional
    public void updateFileData(FileData fileData, Long subsectionID) throws IOException {
        Optional<Subsection> subsectionOptional = this.subsectionRepository.findById(subsectionID);
        if(subsectionOptional.isEmpty()){
            throw new RuntimeException("Subsection not exists");
        }
        Subsection subsection = subsectionOptional.get();

        Optional<FileData> fileDataOptional = fileDataRepository.findFileDataBySubsection_SubsectionID(subsectionID);
        FileData fileDataUpdate = fileDataOptional.orElseGet(FileData::new);

        fileDataUpdate.setName(fileData.getName());
        fileDataUpdate.setType(fileData.getType());
        fileDataUpdate.setData(fileData.getData());
        fileDataUpdate.setSubsection(subsection);

        fileDataRepository.save(fileDataUpdate);
    }

}
