package com.application.mainapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CourseCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseCodeID;

    private String accessCode;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    @OneToOne(mappedBy = "courseCode")
    private ActiveCode activeCode;

    @OneToOne(mappedBy = "courseCode")
    private OrderDetails orderDetails;


    public CourseCode(String accessCode, Course course) {
        this.accessCode = accessCode;
        this.course = course;
    }

}
