package com.vivek.firstmed.patient_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.patient_service.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,String>{

    Optional<Patient> findTopByOrderByPatientIdDesc();
    
}
