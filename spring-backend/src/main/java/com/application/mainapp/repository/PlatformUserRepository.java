package com.application.mainapp.repository;


import com.application.mainapp.dto.platformuser.admin.AdminPanelPlatformUserDTO;
import com.application.mainapp.entities.RoleEnum;
import com.application.mainapp.model.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {

    Optional<PlatformUser> findByEmail(String email);

    @Query("SELECT new com.application.mainapp.dto.platformuser.admin.AdminPanelPlatformUserDTO(ps.platformUserID, ps.email, ps.role.name) " +
            "FROM PlatformUser ps " +
            "WHERE ps.role.name = :role " +
            "ORDER BY ps.platformUserID")
    List<AdminPanelPlatformUserDTO> findPlatformUsersByRole(@Param(("role")) RoleEnum role);
}
