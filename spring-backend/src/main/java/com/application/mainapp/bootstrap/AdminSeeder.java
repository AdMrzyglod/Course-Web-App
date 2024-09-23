package com.application.mainapp.bootstrap;

import com.application.mainapp.dto.platformuser.individualuser.RegisterIndividualUserDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.Admin;
import com.application.mainapp.model.Role;
import com.application.mainapp.repository.AdminRepository;
import com.application.mainapp.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSeeder(RoleRepository roleRepository, AdminRepository adminRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterIndividualUserDTO userDTO = new RegisterIndividualUserDTO();
        userDTO.setPlatformUsername("MAIN_ADMIN");
        userDTO.setPassword("123456");
        userDTO.setEmail("admin@example.com");
        userDTO.setFirstname("AdminFirstname");
        userDTO.setLastname("AdminLastname");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(userDTO.getEmail());

        if (optionalRole.isEmpty() || optionalAdmin.isPresent()) {
            return;
        }

        Admin admin = this.modelMapper.map(userDTO,Admin.class);
        admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        admin.setRole(optionalRole.get());
        admin.setActivationDate(LocalDate.now());
        admin.setAccountActive(true);

        this.adminRepository.save(admin);
    }
}
