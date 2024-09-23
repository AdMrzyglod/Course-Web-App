package com.application.mainapp.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
public class CourseCreateDTO {

    private String name;

    private String description;

    private long categoryID;

    private BigDecimal price;

    private List<SectionCreateDTO> sections;

}
