package com.vivek.firstmed.patient_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivek.firstmed.patient_service.entity.HealthRecord;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, String> {

    Optional<HealthRecord> findTopByOrderByHealthRecordIdDesc();

}
