package com.application.mainapp.controller;


import com.application.mainapp.model.FileData;
import com.application.mainapp.service.FileDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/course/file")
@CrossOrigin(origins="*")
public class FileDataController {

    private final FileDataService fileDataService;

    @Autowired
    public FileDataController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getFileBySubsectionId(@PathVariable Long id) {
        try {
            FileData fileData = this.fileDataService.getFileData(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(fileData.getType()))
                    .body(fileData.getData());
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveSubsectionFileBySubsectionId(@PathVariable Long id, HttpServletRequest request) {
        try {
            boolean isSaved = false;
            JakartaServletFileUpload upload = new JakartaServletFileUpload();
            FileItemInputIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemInput item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.getInputStream();
                if(!item.isFormField() && name.equals("file")){
                    this.fileDataService.saveFileData(
                            FileData.builder()
                                    .name(item.getName())
                                    .type(item.getContentType())
                                    .data(stream.readAllBytes())
                                    .build(),
                            id
                    );
                    isSaved=true;
                    break;
                }
            }

            if(!isSaved){
                throw new RuntimeException("No file");
            }
            return new ResponseEntity<>("Subsection file updated", HttpStatus.OK);
        } catch(RuntimeException | IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateSubsectionFileBySubsectionId(@PathVariable Long id, HttpServletRequest request) {
        try {
            boolean isUpdated = false;
            JakartaServletFileUpload upload = new JakartaServletFileUpload();
            FileItemInputIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemInput item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.getInputStream();
                if(!item.isFormField() && name.equals("file")){
                    this.fileDataService.updateFileData(
                            FileData.builder()
                                    .name(item.getName())
                                    .type(item.getContentType())
                                    .data(stream.readAllBytes())
                                    .build(),
                            id
                    );
                    isUpdated=true;
                    break;
                }
            }

            if(!isUpdated){
                throw new RuntimeException("No image file");
            }
            return new ResponseEntity<>("Subsection file saved", HttpStatus.OK);
        } catch(RuntimeException | IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
