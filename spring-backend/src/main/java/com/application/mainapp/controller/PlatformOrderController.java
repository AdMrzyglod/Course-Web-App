package com.application.mainapp.controller;


import com.application.mainapp.dto.platformorder.PlatformOrderCourseCodeInfoResponseDTO;
import com.application.mainapp.dto.platformorder.PlatformOrderCreateDTO;
import com.application.mainapp.dto.platformorder.PlatformOrderListResponseDTO;
import com.application.mainapp.dto.platformorder.PlatformUserOrderListResponseDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.PlatformUser;
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
@RequestMapping("/api/order")
@CrossOrigin(origins="*")
public class PlatformOrderController {

    private final PlatformOrderService platformOrderService;

    @Autowired
    public PlatformOrderController(PlatformOrderService platformOrderService) {
        this.platformOrderService = platformOrderService;
    }

    @GetMapping("/all/")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PlatformUserOrderListResponseDTO>> getAllOrders(){
        return new ResponseEntity<>(this.platformOrderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/list/")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<PlatformOrderListResponseDTO>> getOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        return new ResponseEntity<>(this.platformOrderService.getOrdersWithCourses(platformUser.getPlatformUserID()), HttpStatus.OK);
    }

    @GetMapping("{orderID}/codes/{courseID}")
    public ResponseEntity<List<PlatformOrderCourseCodeInfoResponseDTO>> getOrderCourseCodes(@PathVariable Long orderID, @PathVariable Long courseID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        if(platformUser.getRole().getName().equals(RoleEnum.EMPLOYEE) || platformUser.getRole().getName().equals(RoleEnum.ADMIN) ||
                platformUser.getRole().getName().equals(RoleEnum.SUPER_ADMIN)){
            return new ResponseEntity<>(this.platformOrderService.getOrderCourseCodes(orderID, courseID), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.platformOrderService.getOrderCourseCodes(orderID, courseID, platformUser.getPlatformUserID()), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> saveOrder(@RequestBody PlatformOrderCreateDTO platformOrderCreateDTO){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            this.platformOrderService.saveOrder(platformUser.getPlatformUserID(), platformOrderCreateDTO);
            return new ResponseEntity<>("Order created", HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deletePlatformOrder(@PathVariable Long id){
        this.platformOrderService.deletePlatformOrder(id);
        return new ResponseEntity<>("PlatformOrder deleted", HttpStatus.OK);
    }
}

