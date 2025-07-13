package com.vivek.firstmed.patient_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vivek.firstmed.patient_service.entity.HealthRecord;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, String> {
    
    @Query(value = "SELECT health_record_id FROM patient_health_records ORDER BY health_record_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopHealthRecordIdsByOrderByHealthRecordIdsDescIncludingDeleted();

}
