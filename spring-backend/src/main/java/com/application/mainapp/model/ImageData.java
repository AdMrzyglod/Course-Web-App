package com.application.mainapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageID;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "courseID")
    private Course course;

    public ImageData(String name, String type, byte[] data, Course course) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.course = course;
    }
}
