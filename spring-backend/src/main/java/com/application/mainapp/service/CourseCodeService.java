package com.application.mainapp.service;


import com.application.mainapp.dto.course.CourseWithCodesDTO;
import com.application.mainapp.dto.coursecode.CourseCodeActiveDTO;
import com.application.mainapp.dto.coursecode.CourseCodeCreateDTO;
import com.application.mainapp.dto.coursecode.CourseCodeAccessCodeDTO;
import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseCodeService {

    private final CourseRepository courseRepository;
    private final CourseCodeRepository courseCodeRepository;
    private final IndividualUserRepository individualUserRepository;
    private final ActiveCodeRepository activeCodeRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CourseCodeService(CourseRepository courseRepository, CourseCodeRepository courseCodeRepository, IndividualUserRepository individualUserRepository, ActiveCodeRepository activeCodeRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.courseCodeRepository = courseCodeRepository;
        this.individualUserRepository = individualUserRepository;
        this.activeCodeRepository = activeCodeRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public void generateCodes(long id, CourseCodeCreateDTO courseCodeCreateDTO){
        Optional<Course> courseOptional = this.courseRepository.findById(id);

        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not exists");
        }
        if(courseCodeCreateDTO.getNumberOfCodes()<=0){
            throw new IllegalArgumentException("Invalid number of codes");
        }

        if(courseCodeCreateDTO.getNumberOfCodes()>10){
            throw new IllegalArgumentException("Code limit is 10");
        }

        for(int i = 0; i< courseCodeCreateDTO.getNumberOfCodes(); i++){
            String uuid = UUID.randomUUID().toString();
            CourseCode courseCode = new CourseCode();
            courseCode.setAccessCode(uuid);
            courseCode.setCourse(courseOptional.get());
            this.courseCodeRepository.save(courseCode);
        }
    }

    public List<CourseCodeAccessCodeDTO> getAllFreeCodes(long id){
         return this.courseCodeRepository.findCourseCodeAccessCodeDTOByOrderDetailsIsNullAndCourseId(id);
    }

    public Optional<CourseWithCodesDTO> getCourseCodesById(Long id){
        Optional<Course> courseOptional =  this.courseRepository.findById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            CourseWithCodesDTO courseWithCodesDTO = this.modelMapper.map(course, CourseWithCodesDTO.class);
            return Optional.of(courseWithCodesDTO);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void activeCourseCode(Long platformUserID, CourseCodeActiveDTO courseCodeActiveDTO){
        Optional<CourseCode> codeOptional =  this.courseCodeRepository.findCourseCodeByAccessCodeAndCourseId(courseCodeActiveDTO.getAccessCode(), courseCodeActiveDTO.getCourseID());

        if(codeOptional.isEmpty()){
            throw new IllegalArgumentException("Course code does not exist");
        }
        CourseCode code = codeOptional.get();

        if(code.getOrderDetails()==null){
            throw new RuntimeException("Code has not been purchased");
        }
        if(code.getActiveCode()!=null){
            throw new RuntimeException("Code was already activated");
        }
        Optional<IndividualUser> userOptional =  this.individualUserRepository.findById(platformUserID);

        if(userOptional.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }

        IndividualUser individualUser = userOptional.get();

        if(this.activeCodeRepository.existsActiveCodeByPlatformUserIdAndCourseId(individualUser.getPlatformUserID(),code.getCourse().getCourseID())){
            throw new RuntimeException("User has already activated the course");
        }

        ActiveCode activeCode = new ActiveCode();
        activeCode.setIndividualUser(individualUser);
        activeCode.setCourseCode(code);

        this.activeCodeRepository.save(activeCode);
    }

    public boolean isCourseActive(Long platformUserID, Long courseID){
        return this.activeCodeRepository.existsActiveCodeByPlatformUserIdAndCourseId(platformUserID,courseID);
    }
}
