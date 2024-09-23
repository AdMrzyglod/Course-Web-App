package com.application.mainapp.dto.platformuser;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserResponseDTO {

    private long platformUserID;
    private String platformUsername;
    private String firstname;
    private String lastname;
    private String email;

}
