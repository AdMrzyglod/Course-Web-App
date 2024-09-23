package com.application.mainapp.controller;


import com.application.mainapp.model.ImageData;
import com.application.mainapp.service.ImageDataService;
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
@RequestMapping("/api/course/image")
@CrossOrigin(origins="*")
public class ImageDataController {

    private final ImageDataService imageDataService;

    @Autowired
    public ImageDataController(ImageDataService imageDataService) {
        this.imageDataService = imageDataService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getImageByCourseId(@PathVariable Long id) {
        try {
            ImageData imageData = this.imageDataService.getImageData(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(imageData.getType()))
                    .body(imageData.getData());
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCourseImageByCourseId(@PathVariable Long id, HttpServletRequest request) {
        try {
            boolean isSaved = false;
            JakartaServletFileUpload upload = new JakartaServletFileUpload();
            FileItemInputIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemInput item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.getInputStream();
                if(!item.isFormField() && name.equals("imageFile")){
                    this.imageDataService.saveImageData(
                            ImageData.builder()
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
                throw new RuntimeException("No image file");
            }
            return new ResponseEntity<>("Course image saved", HttpStatus.OK);
        } catch(RuntimeException | IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCourseImageByCourseId(@PathVariable Long id, HttpServletRequest request) {
        try {
            boolean isUpdated = false;
            JakartaServletFileUpload upload = new JakartaServletFileUpload();
            FileItemInputIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemInput item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.getInputStream();
                if(!item.isFormField() && name.equals("imageFile")){
                    this.imageDataService.updateImageData(
                            ImageData.builder()
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
            return new ResponseEntity<>("Course image updated", HttpStatus.OK);
        } catch(RuntimeException | IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
