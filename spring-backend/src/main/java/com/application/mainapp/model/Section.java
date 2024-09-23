package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionID;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    private String name;

    private String description;

    private int number;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subsection> subsections;


    public Section(Course course, String name, String description, int number) {
        this.course = course;
        this.name = name;
        this.description = description;
        this.number = number;
        this.subsections = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Section{" +
                "section_id=" + sectionID +
                ", course=" + course +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subsections=" + subsections +
                '}';
    }
}
