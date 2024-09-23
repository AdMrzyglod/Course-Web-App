package com.application.mainapp.controller;


import com.application.mainapp.dto.platformuser.UpdateUserStateDTO;
import com.application.mainapp.dto.platformuser.employee.EmployeeAccountResponseDTO;
import com.application.mainapp.dto.platformuser.employee.RegisterEmployeeDTO;
import com.application.mainapp.model.PlatformUser;
import com.application.mainapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   @PostMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
   public ResponseEntity<EmployeeAccountResponseDTO> getEmployeeById(@PathVariable Long id) {
       try{
           return new ResponseEntity<>(this.employeeService.getEmployeeById(id), HttpStatus.OK);
       } catch(Exception e){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
   }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> saveEmployee(@RequestBody RegisterEmployeeDTO registerEmployeeDTO){
        try {
            this.employeeService.saveEmployee(registerEmployeeDTO);
            return new ResponseEntity<>("Employee created", HttpStatus.CREATED);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/currentUser")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<EmployeeAccountResponseDTO> getEmployeeAccountData(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
            return new ResponseEntity<>(this.employeeService.geEmployeeAccountResponseDTO(platformUser.getPlatformUserID()), HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new EmployeeAccountResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateState")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> updateEmployeeState(@RequestBody UpdateUserStateDTO updateUserStateDTO){
        try{
            this.employeeService.updateEmployeeState(updateUserStateDTO);
            return new ResponseEntity<>("Employee updated", HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

