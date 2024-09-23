package com.application.mainapp.dto.course;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseSubsectionDetailsDTO {

    private Long subsectionID;

    private String name;

    private int number;

    private Boolean fileExists;
}
