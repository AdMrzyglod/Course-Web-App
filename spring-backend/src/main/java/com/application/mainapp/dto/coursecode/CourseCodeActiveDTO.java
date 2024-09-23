package com.application.mainapp.dto.coursecode;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseCodeActiveDTO {

    private Long courseID;
    private String accessCode;

}
