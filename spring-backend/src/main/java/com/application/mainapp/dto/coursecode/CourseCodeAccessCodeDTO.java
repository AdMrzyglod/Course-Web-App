package com.application.mainapp.dto.coursecode;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CourseCodeAccessCodeDTO {

    private Long courseCodeID;
    private String accessCode;

    public CourseCodeAccessCodeDTO(Long courseCodeID, String accessCode) {
        this.courseCodeID = courseCodeID;
        this.accessCode = accessCode;
    }
}
