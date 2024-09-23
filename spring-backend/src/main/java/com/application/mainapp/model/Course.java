package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseID;

    private String name;

    private String description;

    @CreationTimestamp
    private Timestamp createTimestamp;
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private ImageData image;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "platformUserID")
    private IndividualUser creator;

    @OneToMany(mappedBy = "course")
    private List<CourseCode> courseCodes;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;


    public Course(String name, String description, Category category, IndividualUser creator) {
        this.name = name;
        this.description = description;
        this.price = BigDecimal.ZERO;
        this.category = category;
        this.creator = creator;
        this.courseCodes = new ArrayList<>();
        this.sections = new ArrayList<>();
    }
}
