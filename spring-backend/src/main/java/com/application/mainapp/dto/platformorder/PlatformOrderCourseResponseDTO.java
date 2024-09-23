package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class PlatformOrderCourseResponseDTO {

    private long courseID;
    private String name;
    private BigDecimal coursePrice;
    private long numberOfCourseCodes;
    private BigDecimal courseCodesPrice;

    public PlatformOrderCourseResponseDTO(long courseID, String name, BigDecimal coursePrice, long numberOfCourseCodes, BigDecimal courseCodesPrice) {
        this.courseID = courseID;
        this.name = name;
        this.coursePrice = coursePrice;
        this.numberOfCourseCodes = numberOfCourseCodes;
        this.courseCodesPrice = courseCodesPrice;
    }
}
