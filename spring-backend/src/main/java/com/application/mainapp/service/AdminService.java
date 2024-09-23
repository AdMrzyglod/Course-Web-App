package com.application.mainapp.service;


import com.application.mainapp.dto.platformuser.UpdateUserStateDTO;
import com.application.mainapp.dto.platformuser.admin.AdminAccountResponseDTO;
import com.application.mainapp.dto.platformuser.admin.RegisterAdminDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.Admin;
import com.application.mainapp.model.Role;
import com.application.mainapp.repository.AdminRepository;
import com.application.mainapp.repository.PlatformUserRepository;
import com.application.mainapp.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PlatformUserRepository platformUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, PlatformUserRepository platformUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.platformUserRepository = platformUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public void saveAdmin(RegisterAdminDTO registerAdminDTO){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role does not exist");
        }

        if(this.platformUserRepository.findByEmail(registerAdminDTO.getEmail()).isPresent()){
            throw new RuntimeException("User with email already exists");
        }

        Admin admin = this.modelMapper.map(registerAdminDTO, Admin.class);
        admin.setPlatformUsername(registerAdminDTO.getPlatformUsername());
        admin.setAccountActive(true);
        admin.setPassword(passwordEncoder.encode(registerAdminDTO.getPassword()));
        admin.setRole(optionalRole.get());

        this.adminRepository.save(admin);
    }

    public AdminAccountResponseDTO getAdminById(Long id){
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return modelMapper.map(admin, AdminAccountResponseDTO.class);
        } else {
            throw new RuntimeException("Admin not exists");
        }
    }

    public AdminAccountResponseDTO getAdminAccountResponseDTO(Long platformUserID){
        Optional<Admin> adminOptional = this.adminRepository.findById(platformUserID);
        if(adminOptional.isPresent()){
            return this.modelMapper.map(adminOptional, AdminAccountResponseDTO.class);
        }
        throw new RuntimeException("Admin not exists");
    }

    public void updateAdminState(UpdateUserStateDTO updateUserStateDTO){
        Optional<Admin> adminOptional = this.adminRepository.findById(updateUserStateDTO.getPlatformUserID());
        if(adminOptional.isPresent()){
            Admin admin = adminOptional.get();
            admin.setAccountActive(updateUserStateDTO.isAccountActive());
            this.adminRepository.save(admin);
        }
        else{
            throw new RuntimeException("Admin not exists");
        }
    }
}
