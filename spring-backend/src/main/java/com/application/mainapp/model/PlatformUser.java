package com.application.mainapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class PlatformUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long platformUserID;

    private String platformUsername;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID")
    private Role role;


    public PlatformUser(long platformUserID, String platformUsername, String password, String email, String firstname, String lastname, Role role) {
        this.platformUserID = platformUserID;
        this.platformUsername = platformUsername;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());

        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public PlatformUser setRole(Role role) {
        this.role = role;
        return this;
    }
}