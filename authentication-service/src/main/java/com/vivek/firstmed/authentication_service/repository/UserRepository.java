package com.vivek.firstmed.authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.authentication_service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
