package com.application.mainapp.service;


import com.application.mainapp.dto.course.CourseCreateDTO;
import com.application.mainapp.dto.course.CourseListResponseDTO;
import com.application.mainapp.dto.course.CourseDetailsDTO;
import com.application.mainapp.model.Course;
import com.application.mainapp.model.Section;
import com.application.mainapp.model.Subsection;
import com.application.mainapp.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final IndividualUserRepository individualUserRepository;
    private final CategoryRepository categoryRepository;
    private final SectionRepository sectionRepository;
    private final SubsectionRepository subsectionRepository;
    private final CourseCodeRepository courseCodeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, IndividualUserRepository individualUserRepository, CategoryRepository categoryRepository, SectionRepository sectionRepository, SubsectionRepository subsectionRepository, CourseCodeRepository courseCodeRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.individualUserRepository = individualUserRepository;
        this.categoryRepository = categoryRepository;
        this.sectionRepository = sectionRepository;
        this.subsectionRepository = subsectionRepository;
        this.courseCodeRepository = courseCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public long saveCourse(Long platformUserID, CourseCreateDTO courseCreateDTO) throws IOException {
        if(this.individualUserRepository.findById(platformUserID).isEmpty()){
            throw new RuntimeException("User not exists");
        }
        if(this.categoryRepository.findById(courseCreateDTO.getCategoryID()).isEmpty()){
            throw new RuntimeException("Category not exists");
        }
        Course course = new Course();
        course.setName(courseCreateDTO.getName());
        course.setDescription(courseCreateDTO.getDescription());
        course.setCategory(this.categoryRepository.findById(courseCreateDTO.getCategoryID()).get());
        course.setCreator(this.individualUserRepository.findById(platformUserID).get());
        course.setPrice(courseCreateDTO.getPrice());
        course = this.courseRepository.save(course);

        Course finalCourse = course;
        List<Section> sectionList = courseCreateDTO.getSections().stream()
                .map(sectionCreateDTO -> {
                    Section section = new Section();
                    section.setCourse(finalCourse);
                    section.setName(sectionCreateDTO.getName());
                    section.setDescription(sectionCreateDTO.getDescription());
                    section.setNumber(sectionCreateDTO.getNumber());
                    section=this.sectionRepository.save(section);

                    Section finalSection = section;
                    List<Subsection> subsectionList = sectionCreateDTO.getSubsections().stream()
                            .map(subsectionDTO -> {
                                Subsection subsection = new Subsection(subsectionDTO.getName(), finalSection, subsectionDTO.getNumber());
                                subsection = this.subsectionRepository.save(subsection);
                                return subsection;
                            })
                            .collect(Collectors.toList());
                    section.setSubsections(subsectionList);

                    return section;
                })
                .toList();
        course.setSections(sectionList);

        return course.getCourseID();
    }

    public List<CourseListResponseDTO> getAllCourses(){
         List<Course> courseList = this.courseRepository.findAll();
         return courseList.stream()
                 .map(course -> modelMapper.map(course, CourseListResponseDTO.class))
                 .collect(Collectors.toList());
    }

    @Transactional
    public List<CourseListResponseDTO> getIndividualUserCourses(Long platformUserID){
        List<Course> courseList = this.courseRepository.findCoursesByPlatformUserID(platformUserID);
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseListResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CourseListResponseDTO> getIndividualUserCreateCourses(Long platformUserID){
        List<Course> courseList = this.courseRepository.findCreateCoursesByPlatformUserID(platformUserID);
        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseListResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<CourseDetailsDTO> getCourseById(Long id){
        Optional<Course> courseOptional =  this.courseRepository.findById(id);

        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            CourseDetailsDTO courseDetailsDTO = this.modelMapper.map(course, CourseDetailsDTO.class);
            courseDetailsDTO.setNumberOfFreeCodes(this.courseCodeRepository.findNumberOfCourseCodesByOrderDetailsIsNullAndCourseId(id));
            return Optional.of(courseDetailsDTO);
        } else {
            return Optional.empty();
        }
    }
}
