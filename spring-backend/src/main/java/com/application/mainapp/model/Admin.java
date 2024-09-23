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
public class Admin extends Employee {

    public Admin(long platformUserID, String platformUsername, String password, String email, String firstname, String lastname, Role role, LocalDate activationDate, boolean isAccountActive) {
        super(platformUserID, platformUsername, password, email, firstname, lastname, role, activationDate, isAccountActive);
    }
}
