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
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileID;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "subsectionID")
    private Subsection subsection;

    public FileData(String name, String type, byte[] data, Subsection subsection) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.subsection = subsection;
    }
}
