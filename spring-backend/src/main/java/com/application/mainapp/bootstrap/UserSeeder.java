package com.application.mainapp.bootstrap;

import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserSeeder {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final IndividualUserRepository individualUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepository paymentRepository;

    public void seedMainAdmin() {
        if (adminRepository.findByEmail("admin1@test.com").isPresent()) {
            return;
        }

        Role superAdminRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN).orElseThrow();
        Admin admin = new Admin();
        admin.setPlatformUsername("admin1");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setEmail("admin1@test.com");
        admin.setFirstname("firstname1");
        admin.setLastname("lastname1");
        admin.setRole(superAdminRole);
        admin.setActivationDate(LocalDate.now());
        admin.setAccountActive(true);
        adminRepository.save(admin);
    }

    public void seedMockUsers() {
        if (employeeRepository.count() > 1) {
            return;
        }

        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow();
        Role employeeRole = roleRepository.findByName(RoleEnum.EMPLOYEE).orElseThrow();
        Role userRole = roleRepository.findByName(RoleEnum.USER).orElseThrow();

        Admin admin2 = new Admin();
        admin2.setPlatformUsername("admin2");
        admin2.setPassword(passwordEncoder.encode("123456"));
        admin2.setEmail("admin2@test.com");
        admin2.setFirstname("firstname2");
        admin2.setLastname("lastname2");
        admin2.setRole(adminRole);
        admin2.setActivationDate(LocalDate.now());
        admin2.setAccountActive(true);
        adminRepository.save(admin2);

        Employee emp1 = new Employee();
        emp1.setPlatformUsername("employee1");
        emp1.setPassword(passwordEncoder.encode("123456"));
        emp1.setEmail("employee1@test.com");
        emp1.setFirstname("firstname3");
        emp1.setLastname("lastname3");
        emp1.setRole(employeeRole);
        emp1.setActivationDate(LocalDate.now());
        emp1.setAccountActive(true);

        Employee emp2 = new Employee();
        emp2.setPlatformUsername("employee2");
        emp2.setPassword(passwordEncoder.encode("123456"));
        emp2.setEmail("employee2@test.com");
        emp2.setFirstname("firstname4");
        emp2.setLastname("lastname4");
        emp2.setRole(employeeRole);
        emp2.setActivationDate(LocalDate.now());
        emp2.setAccountActive(true);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        for (int i = 1; i <= 4; i++) {
            IndividualUser user = new IndividualUser();
            user.setPlatformUsername("user" + i);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEmail("user" + i + "@test.com");
            user.setFirstname("firstname" + (i + 4));
            user.setLastname("lastname" + (i + 4));
            user.setRole(userRole);
            user.setMoney(BigDecimal.valueOf(203.22 * i));
            IndividualUser savedUser = individualUserRepository.save(user);

            for (int j = 1; j <= 2; j++) {
                Payment payment = new Payment();
                payment.setTitle("Wpłata " + j + i);
                payment.setBankAccountNumber("11 1111 1111 2222 2222 2222 2222");
                payment.setAmount(BigDecimal.valueOf(2000.00 * j * i));
                payment.setReceiptDate(java.sql.Date.valueOf(LocalDate.now().minusDays(j*i)));
                payment.setIndividualUser(savedUser);

                paymentRepository.save(payment);
            }
        }
    }
}