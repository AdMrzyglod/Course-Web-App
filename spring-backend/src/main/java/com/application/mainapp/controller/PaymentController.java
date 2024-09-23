package com.application.mainapp.controller;


import com.application.mainapp.dto.payment.PaymentCreateDTO;
import com.application.mainapp.dto.payment.PaymentListResponseDTO;
import com.application.mainapp.dto.payment.PaymentResponseDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins="*")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
        return new ResponseEntity<>(this.paymentService.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping("/list/")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<PaymentListResponseDTO>> getPayments(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        return new ResponseEntity<>(this.paymentService.getIndividualUserPayments(platformUser.getPlatformUserID()), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> savePayment(@RequestBody PaymentCreateDTO paymentCreateDTO){
        try{
            this.paymentService.savePayment(paymentCreateDTO);
            return new ResponseEntity<>("Payment created", HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deletePayment(@PathVariable Long id){
        try{
            this.paymentService.deletePayment(id);
            return new ResponseEntity<>("Payment deleted", HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
