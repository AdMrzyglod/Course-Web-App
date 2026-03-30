package com.application.mainapp.bootstrap;

import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.Role;
import com.application.mainapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepository roleRepository;

    public void seed() {
        if (roleRepository.count() > 0) {
            return;
        }

        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.USER, RoleEnum.EMPLOYEE, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.EMPLOYEE, "Employee role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Role roleToCreate = new Role();
            roleToCreate.setName(roleName);
            roleToCreate.setDescription(roleDescriptionMap.get(roleName));
            roleRepository.save(roleToCreate);
        });
    }
}