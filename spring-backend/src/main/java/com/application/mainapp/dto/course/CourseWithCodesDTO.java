package com.application.mainapp.dto.course;

import com.application.mainapp.dto.coursecode.CourseCodeAccessCodeDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class CourseWithCodesDTO {

    private Long course_id;
    private String name;
    private String description;
    private Timestamp createTimestamp;
    private BigDecimal price;
    private List<CourseCodeAccessCodeDTO> courseCodes;

    public CourseWithCodesDTO() {
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CourseCodeAccessCodeDTO> getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(List<CourseCodeAccessCodeDTO> courseCodes) {
        this.courseCodes = courseCodes;
    }
}
