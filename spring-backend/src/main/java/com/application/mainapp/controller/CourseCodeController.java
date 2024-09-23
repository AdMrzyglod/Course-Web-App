package com.application.mainapp.controller;


import com.application.mainapp.dto.coursecode.CourseCodeActiveDTO;
import com.application.mainapp.dto.coursecode.CourseCodeCreateDTO;
import com.application.mainapp.dto.coursecode.CourseCodeAccessCodeDTO;
import com.application.mainapp.dto.course.CourseWithCodesDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.CourseCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseCodeController {

    private final CourseCodeService courseCodeService;


    @Autowired
    public CourseCodeController(CourseCodeService courseCodeService) {
        this.courseCodeService = courseCodeService;
    }

    @GetMapping("/{id}/freeCodes")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<CourseCodeAccessCodeDTO>> getAllFreeCodes(@PathVariable Long id){
        return new ResponseEntity<>(this.courseCodeService.getAllFreeCodes(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/codes")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CourseWithCodesDTO> getCourseCodesById(@PathVariable Long id) {
        Optional<CourseWithCodesDTO> courseOptional = this.courseCodeService.getCourseCodesById(id);
        return courseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/createCodes")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> generateNewCodes(@PathVariable long id, @RequestBody CourseCodeCreateDTO courseCodeCreateDTO){
        try {
            this.courseCodeService.generateCodes(id, courseCodeCreateDTO);
            return new ResponseEntity<>("Codes created", HttpStatus.OK);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activeCode")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> activeCourseCodes(@RequestBody CourseCodeActiveDTO courseCodeActiveDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            this.courseCodeService.activeCourseCode(platformUser.getPlatformUserID(), courseCodeActiveDTO);
            return new ResponseEntity<>("Codes active", HttpStatus.CREATED);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{courseID}/courseActive")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Boolean> activeCourseCode(@PathVariable Long courseID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        boolean isActive = this.courseCodeService.isCourseActive(platformUser.getPlatformUserID(), courseID);
        return new ResponseEntity<>(isActive, HttpStatus.OK);
    }
}
