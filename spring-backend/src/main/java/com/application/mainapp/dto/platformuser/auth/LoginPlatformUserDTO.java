package com.application.mainapp.dto.platformuser.auth;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginPlatformUserDTO {

    private String email;
    private String password;
}
