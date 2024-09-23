package com.application.mainapp.controller;


import com.application.mainapp.dto.platformuser.auth.LoginPlatformUserDTO;
import com.application.mainapp.dto.platformuser.auth.LoginResponseDTO;
import com.application.mainapp.dto.platformuser.UserResponseDTO;
import com.application.mainapp.dto.platformuser.individualuser.RegisterIndividualUserDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.application.mainapp.service.JwtService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    @Autowired
    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterIndividualUserDTO registerIndividualUserDTO) {
        try {
            UserResponseDTO registeredUser = authService.signup(registerIndividualUserDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginPlatformUserDTO loginPlatformUserDTO) {
        try{
            PlatformUser authenticatedUser = authService.authenticate(loginPlatformUserDTO);

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponseDTO loginResponse = new LoginResponseDTO();
            loginResponse.setToken(jwtToken);

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}