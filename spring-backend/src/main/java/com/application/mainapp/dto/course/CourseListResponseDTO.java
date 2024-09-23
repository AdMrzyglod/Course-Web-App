package com.application.mainapp.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
public class CourseListResponseDTO {

    private Long courseID;
    private String name;
    private Timestamp createTimestamp;
    private BigDecimal price;
    private CourseCategoryListDTO category;
    private CoursePlatformUserListDTO creator;

}
