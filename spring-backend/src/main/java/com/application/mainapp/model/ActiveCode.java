package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ActiveCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeCodeID;

    @OneToOne
    @JoinColumn(name = "courseCodeID")
    private CourseCode courseCode;

    @ManyToOne
    @JoinColumn(name = "platformUserID")
    private IndividualUser individualUser;

    public ActiveCode(CourseCode courseCode, IndividualUser individualUser) {
        this.courseCode = courseCode;
        this.individualUser = individualUser;
    }

}
