package com.application.mainapp.controller;


import com.application.mainapp.dto.platformuser.admin.AdminPanelPlatformUserDTO;
import com.application.mainapp.dto.platformuser.PlatformUserRoleDTO;
import com.application.mainapp.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platformUser")
public class PlatformUserController {

    private final PlatformUserService platformUserService;


    @Autowired
    public PlatformUserController(PlatformUserService platformUserService) {
        this.platformUserService = platformUserService;
    }


    @PostMapping("/findByRole")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<AdminPanelPlatformUserDTO>> getPlatformUsersByRole(@RequestBody PlatformUserRoleDTO platformUserRoleDTO){
        return new ResponseEntity<>(this.platformUserService.getPlatformUsersByRoleName(platformUserRoleDTO), HttpStatus.OK);
    }
}

