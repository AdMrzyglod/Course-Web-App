package com.application.mainapp.controller;


import com.application.mainapp.dto.course.CourseCreateDTO;
import com.application.mainapp.dto.course.CourseDetailsDTO;
import com.application.mainapp.dto.course.CourseListResponseDTO;
import com.application.mainapp.dto.course.CourseResponseDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins="*")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseListResponseDTO>> getAllCourses(){
        return new ResponseEntity<>(this.courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseDetailsDTO> courseOptional = this.courseService.getCourseById(id);
        return courseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseResponseDTO> saveCourse(@RequestBody CourseCreateDTO courseCreateDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            long courseID = this.courseService.saveCourse(platformUser.getPlatformUserID(), courseCreateDTO);
            return new ResponseEntity<>(CourseResponseDTO.builder().courseID(courseID).message("Course created").build(), HttpStatus.CREATED);
        } catch(RuntimeException | IOException e){
            return new ResponseEntity<>(CourseResponseDTO.builder().message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list/")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<CourseListResponseDTO>> getIndividualUserCourses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        return new ResponseEntity<>(this.courseService.getIndividualUserCourses(platformUser.getPlatformUserID()), HttpStatus.OK);
    }


    @GetMapping("/list-create/")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<CourseListResponseDTO>> getIndividualUserCreateCourses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        return new ResponseEntity<>(this.courseService.getIndividualUserCreateCourses(platformUser.getPlatformUserID()), HttpStatus.OK);
    }
}
