package com.application.mainapp.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
public class CourseDetailsDTO {

    private Long courseID;

    private String name;

    private String description;

    private Timestamp createTimestamp;

    private int numberOfFreeCodes;

    private BigDecimal price;

    private CourseCategoryDetailsDTO category;

    private CoursePlatformUserDetailsDTO creator;

    private List<CourseSectionDetailsDTO> sections;

}
