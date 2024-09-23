package com.application.mainapp.service;

import com.application.mainapp.dto.platformuser.auth.LoginPlatformUserDTO;
import com.application.mainapp.dto.platformuser.UserResponseDTO;
import com.application.mainapp.dto.platformuser.individualuser.RegisterIndividualUserDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {
    private final PlatformUserRepository platformUserRepository;
    private final IndividualUserRepository individualUserRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(PlatformUserRepository platformUserRepository, IndividualUserRepository individualUserRepository,
                       EmployeeRepository employeeRepository, AdminRepository adminRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.platformUserRepository = platformUserRepository;
        this.individualUserRepository = individualUserRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO signup(RegisterIndividualUserDTO registerIndividualUserDTO) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role does not exist");
        }

        if(this.platformUserRepository.findByEmail(registerIndividualUserDTO.getEmail()).isPresent()){
            throw new RuntimeException("User with email already exists");
        }
        IndividualUser individualUser = this.modelMapper.map(registerIndividualUserDTO, IndividualUser.class);
        individualUser.setPassword(passwordEncoder.encode(registerIndividualUserDTO.getPassword()));
        individualUser.setRole(optionalRole.get());
        individualUser.setMoney(BigDecimal.ZERO);

        individualUser = this.individualUserRepository.save(individualUser);

        return this.modelMapper.map(individualUser, UserResponseDTO.class);
    }

    public PlatformUser authenticate(LoginPlatformUserDTO loginPlatformUserDTO) {
        Optional<PlatformUser> optionalPlatformUser = platformUserRepository.findByEmail(loginPlatformUserDTO.getEmail());
        if(optionalPlatformUser.isPresent()){
            PlatformUser platformUser = optionalPlatformUser.get();

            if(platformUser.getRole().getName().equals(RoleEnum.EMPLOYEE)){
                Employee employee = this.employeeRepository.findById(platformUser.getPlatformUserID()).get();
                checkActivationDateAndState(employee.getActivationDate(), employee.isAccountActive());
            }
            else if(platformUser.getRole().getName().equals(RoleEnum.ADMIN)){
                Admin admin = this.adminRepository.findById(platformUser.getPlatformUserID()).get();
                checkActivationDateAndState(admin.getActivationDate(), admin.isAccountActive());
            }
        }

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginPlatformUserDTO.getEmail(),
                        loginPlatformUserDTO.getPassword()
                )
        );

        return optionalPlatformUser
                .orElseThrow();
    }

    private void checkActivationDateAndState(LocalDate activationDate, boolean isAccountActive){
        if (!isAccountActive || activationDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Inactive account");
        }
    }
}
