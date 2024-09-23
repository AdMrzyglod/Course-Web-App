package com.application.mainapp.model;


import com.application.mainapp.entities.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleID;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    private String description;

    public Role(RoleEnum name, String description) {
        this.name = name;
        this.description = description;
    }
}
