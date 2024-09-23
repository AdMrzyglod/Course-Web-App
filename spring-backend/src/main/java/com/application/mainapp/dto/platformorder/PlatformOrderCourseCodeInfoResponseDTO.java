package com.application.mainapp.dto.platformorder;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlatformOrderCourseCodeInfoResponseDTO {

    private Long courseCodeID;
    private String accessCode;
    private boolean activated;

    public PlatformOrderCourseCodeInfoResponseDTO(Long courseCodeID, String accessCode, boolean activated) {
        this.courseCodeID = courseCodeID;
        this.accessCode = accessCode;
        this.activated = activated;
    }
}
