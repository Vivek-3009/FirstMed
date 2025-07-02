package com.vivek.firstmed.doctor_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.doctor_service.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Optional<Doctor> findTopByOrderByDoctorIdDesc();
    Optional<Doctor> findBySpecialization(String specialization);
    Optional<Doctor> findByLocation(String location);
    
    
}
