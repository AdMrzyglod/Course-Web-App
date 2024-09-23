package com.application.mainapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailsID;

    @Column(name = "coursePrice", precision = 10, scale = 2)
    private BigDecimal coursePrice;

    @OneToOne
    @JoinColumn(name = "courseCodeID")
    private CourseCode courseCode;

    @ManyToOne
    @JoinColumn(name="platformOrderID")
    private PlatformOrder platformOrder;


    public OrderDetails(BigDecimal coursePrice, CourseCode courseCode, PlatformOrder platformOrder) {
        this.coursePrice = coursePrice;
        this.courseCode = courseCode;
        this.platformOrder = platformOrder;
    }

}
