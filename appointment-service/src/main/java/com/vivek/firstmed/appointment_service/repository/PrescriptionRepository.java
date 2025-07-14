package com.vivek.firstmed.appointment_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vivek.firstmed.appointment_service.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    
    @Query(value = "SELECT prescription_id FROM prescriptions ORDER BY prescription_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopPrescriptionIdByOrderByPrescriptionIdDescIncludingDeleted();
    
    
}
