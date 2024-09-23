package com.application.mainapp.entities;

public enum RoleEnum {
    USER,
    EMPLOYEE,
    ADMIN,
    SUPER_ADMIN;

    public static RoleEnum fromString(String role) {
        try {
            return RoleEnum.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect role");
            return null;
        }
    }
}

