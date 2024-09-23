package com.application.mainapp.controller;


import com.application.mainapp.dto.platformorder.PlatformOrderResponseDTO;
import com.application.mainapp.dto.platformuser.UserResponseDTO;
import com.application.mainapp.dto.platformuser.individualuser.IndividualUserAccountResponseDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.IndividualUserService;
import com.application.mainapp.service.PlatformOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/individualUser")
public class IndividualUserController {

    private final IndividualUserService individualUserService;
    private final PlatformOrderService platformOrderService;

    @Autowired
    public IndividualUserController(IndividualUserService individualUserService, PlatformOrderService platformOrderService) {
        this.individualUserService = individualUserService;
        this.platformOrderService = platformOrderService;
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllIndividualUsers(){
        return new ResponseEntity<>(this.individualUserService.getAllIndividualUsers(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<IndividualUserAccountResponseDTO> getIndividualUserById(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(this.individualUserService.getIndividualUserById(id), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PlatformOrderResponseDTO>> getIndividualUserOrdersById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser currentUser = (PlatformUser) authentication.getPrincipal();
        return new ResponseEntity<>(this.platformOrderService.getIndividualUserOrders(currentUser.getPlatformUserID()), HttpStatus.OK);
    }

    @PostMapping("/currentUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IndividualUserAccountResponseDTO> getIndividualUserAccountData(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            return new ResponseEntity<>(this.individualUserService.getIndividualUserAccountResponseDTO(platformUser.getPlatformUserID()), HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new IndividualUserAccountResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }
}

