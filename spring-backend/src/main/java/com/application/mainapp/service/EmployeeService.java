package com.application.mainapp.service;


import com.application.mainapp.dto.platformuser.UpdateUserStateDTO;
import com.application.mainapp.dto.platformuser.employee.EmployeeAccountResponseDTO;
import com.application.mainapp.dto.platformuser.employee.RegisterEmployeeDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.Employee;
import com.application.mainapp.model.Role;
import com.application.mainapp.repository.EmployeeRepository;
import com.application.mainapp.repository.PlatformUserRepository;
import com.application.mainapp.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PlatformUserRepository platformUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PlatformUserRepository platformUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.platformUserRepository = platformUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }



    @Transactional
    public void saveEmployee(RegisterEmployeeDTO registerEmployeeDTO){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.EMPLOYEE);

        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role does not exist");
        }

        if(this.platformUserRepository.findByEmail(registerEmployeeDTO.getEmail()).isPresent()){
            throw new RuntimeException("User with email already exists");
        }

        Employee employee = this.modelMapper.map(registerEmployeeDTO, Employee.class);
        employee.setPlatformUsername(registerEmployeeDTO.getPlatformUsername());
        employee.setAccountActive(true);
        employee.setPassword(passwordEncoder.encode(registerEmployeeDTO.getPassword()));
        employee.setRole(optionalRole.get());

        this.employeeRepository.save(employee);
    }

    public EmployeeAccountResponseDTO getEmployeeById(Long id){
        Optional<Employee> employeeOptional = this.employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return modelMapper.map(employee, EmployeeAccountResponseDTO.class);
        } else {
            throw new RuntimeException("Employee not exists");
        }
    }

    public EmployeeAccountResponseDTO geEmployeeAccountResponseDTO(Long platformUserID){
        Optional<Employee> employeeOptional = this.employeeRepository.findById(platformUserID);
        if(employeeOptional.isPresent()){
            return this.modelMapper.map(employeeOptional, EmployeeAccountResponseDTO.class);
        }
        throw new RuntimeException("Employee not exists");
    }

    public void updateEmployeeState(UpdateUserStateDTO updateUserStateDTO){
        Optional<Employee> employeeOptional = this.employeeRepository.findById(updateUserStateDTO.getPlatformUserID());
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setAccountActive(updateUserStateDTO.isAccountActive());
            this.employeeRepository.save(employee);
        }
        else{
            throw new RuntimeException("Employee not exists");
        }
    }
}
