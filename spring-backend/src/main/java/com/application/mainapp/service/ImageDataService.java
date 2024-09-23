package com.application.mainapp.service;


import com.application.mainapp.model.Course;
import com.application.mainapp.model.ImageData;
import com.application.mainapp.repository.CourseRepository;
import com.application.mainapp.repository.ImageDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ImageDataService(ImageDataRepository imageDataRepository, CourseRepository courseRepository) {
        this.imageDataRepository =  imageDataRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public ImageData getImageData(Long courseID) {
        Optional<ImageData> imageDataOptional = imageDataRepository.findImageDataByCourse_CourseID(courseID);

        if(imageDataOptional.isPresent()) {
            return imageDataOptional.get();
        }
        throw new RuntimeException("No image found for the course id:" + courseID);
    }

    @Transactional
    public void saveImageData(ImageData imageData, Long courseId) throws IOException {
        Optional<Course> courseOptional = this.courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not exists");
        }
        Course course = courseOptional.get();

        imageData.setCourse(course);

        imageDataRepository.save(imageData);
    }


    @Transactional
    public void updateImageData(ImageData imageData, Long courseId) throws IOException {
        Optional<Course> courseOptional = this.courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not exists");
        }
        Course course = courseOptional.get();

        Optional<ImageData> imageDataOptional = imageDataRepository.findImageDataByCourse_CourseID(courseId);
        ImageData imageDataUpdate;
        imageDataUpdate = imageDataOptional.orElseGet(ImageData::new);

        imageDataUpdate.setName(imageData.getName());
        imageDataUpdate.setType(imageData.getType());
        imageDataUpdate.setData(imageData.getData());
        imageDataUpdate.setCourse(course);

        imageDataRepository.save(imageDataUpdate);
    }
}
