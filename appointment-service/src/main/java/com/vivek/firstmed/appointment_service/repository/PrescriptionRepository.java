package com.vivek.firstmed.appointment_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.appointment_service.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    
    Optional<Prescription> findTopByOrderByPrescriptionIdDesc();
    
    
}
