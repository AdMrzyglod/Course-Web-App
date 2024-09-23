package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Subsection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long subsectionID;

    private String name;

    private int number;

    @OneToOne(mappedBy = "subsection", cascade = CascadeType.ALL)
    private FileData file;

    @ManyToOne
    @JoinColumn(name = "sectionID")
    private Section section;


    public Subsection(String name, Section section, int number) {
        this.name = name;
        this.section = section;
        this.number=number;
    }

}