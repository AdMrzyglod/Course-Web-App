package com.application.mainapp.dto.platformuser;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegisterUserDTO {

    private String platformUsername;
    private String password;
    private String email;
    private String firstname;
    private String lastname;

}
