package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryID;

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Course> courses;



    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.courses = new ArrayList<>();
    }
}
