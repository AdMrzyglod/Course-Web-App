package com.application.mainapp.service;


import com.application.mainapp.dto.platformuser.PlatformUserRoleDTO;
import com.application.mainapp.dto.platformuser.admin.AdminPanelPlatformUserDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.repository.PlatformUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatformUserService {

    private final PlatformUserRepository platformUserRepository;


    @Autowired
    public PlatformUserService(PlatformUserRepository platformUserRepository) {
        this.platformUserRepository = platformUserRepository;
    }

    public List<AdminPanelPlatformUserDTO> getPlatformUsersByRoleName(PlatformUserRoleDTO platformUserRoleDTO){
        RoleEnum roleEnum = RoleEnum.fromString(platformUserRoleDTO.getRole());
        if(roleEnum==null){
            return new ArrayList<>();
        }
        return this.platformUserRepository.findPlatformUsersByRole(roleEnum);
    }
}
