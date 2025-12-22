package com.vivek.firstmed.authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.authentication_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
