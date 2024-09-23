package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class IndividualUser extends PlatformUser {


    @Column(name = "money", precision = 10, scale = 2)
    private BigDecimal money;

    @OneToMany(mappedBy = "creator")
    private List<Course> courses;

    @OneToMany(mappedBy = "individualUser")
    private List<ActiveCode> activeCodes;

    @OneToMany(mappedBy = "individualUser")
    private List<PlatformOrder> platformOrders;

    @OneToMany(mappedBy = "individualUser")
    private List<Payment> payments;

    public IndividualUser(long platformUserID, String platformUsername, String password, String email, String firstname, String lastname, Role role) {
        super(platformUserID, platformUsername, password, email, firstname, lastname, role);
        this.money = BigDecimal.ZERO;
        this.courses = new ArrayList<>();
        this.activeCodes = new ArrayList<>();
        this.platformOrders = new ArrayList<>();
        this.payments = new ArrayList<>();
    }
}
