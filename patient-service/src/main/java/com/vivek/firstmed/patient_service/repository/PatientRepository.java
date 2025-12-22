package com.vivek.firstmed.patient_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vivek.firstmed.patient_service.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,String>{


    @Query(value = "SELECT patient_id FROM patients ORDER BY patient_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopPatientIdsByOrderByPatientIdsDescIncludingDeleted();
    
}
