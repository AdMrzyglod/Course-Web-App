package com.application.mainapp.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Employee extends PlatformUser {

    private LocalDate activationDate;
    private boolean isAccountActive;

    public Employee(long platformUserID, String platformUsername, String password, String email, String firstname, String lastname, Role role, LocalDate activationDate, boolean isAccountActive) {
        super(platformUserID, platformUsername, password, email, firstname, lastname, role);
        this.activationDate = activationDate;
        this.isAccountActive = isAccountActive;
    }
}
