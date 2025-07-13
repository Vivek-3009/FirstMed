package com.vivek.firstmed.doctor_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vivek.firstmed.doctor_service.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    
    @Query(value = "SELECT doctor_id FROM doctor ORDER BY doctor_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopDoctorIdByOrderByDoctorIdDescIncludingDeleted();

    Optional<Doctor> findBySpecialization(String specialization);

    @Query("SELECT d FROM Doctor d WHERE d.address LIKE %:location%")
    Optional<Doctor> findByLocation(@Param("location") String location);

}
