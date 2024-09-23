package com.application.mainapp.dto.platformuser.admin;


import com.application.mainapp.entities.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AdminPanelPlatformUserDTO {

    private long platformUserID;
    private String email;
    private String role;

    public AdminPanelPlatformUserDTO(long platformUserID, String email, RoleEnum role) {
        this.platformUserID = platformUserID;
        this.email = email;
        this.role = role.toString();
    }
}
