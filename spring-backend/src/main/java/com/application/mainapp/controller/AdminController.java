package com.application.mainapp.controller;


import com.application.mainapp.dto.platformuser.UpdateUserStateDTO;
import com.application.mainapp.dto.platformuser.admin.AdminAccountResponseDTO;
import com.application.mainapp.dto.platformuser.admin.RegisterAdminDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


   @PostMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
   public ResponseEntity<AdminAccountResponseDTO> getAdminById(@PathVariable Long id) {
       try{
           return new ResponseEntity<>(this.adminService.getAdminById(id), HttpStatus.OK);
       } catch(Exception e){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
   }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<String> saveAdmin(@RequestBody RegisterAdminDTO registerAdminDTO){
        try {
            this.adminService.saveAdmin(registerAdminDTO);
            return new ResponseEntity<>("Admin created", HttpStatus.CREATED);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/currentUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AdminAccountResponseDTO> getAdminAccountData(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            return new ResponseEntity<>(this.adminService.getAdminAccountResponseDTO(platformUser.getPlatformUserID()), HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new AdminAccountResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateState")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<String> updateAdminState(@RequestBody UpdateUserStateDTO updateUserStateDTO){
        try{
            this.adminService.updateAdminState(updateUserStateDTO);
            return new ResponseEntity<>("Admin updated", HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

