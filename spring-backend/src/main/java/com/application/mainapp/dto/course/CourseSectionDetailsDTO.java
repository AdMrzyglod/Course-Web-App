package com.application.mainapp.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CourseSectionDetailsDTO {

    private Long sectionID;

    private String name;

    private String description;

    private int number;

    private List<CourseSubsectionDetailsDTO> subsections;

}
