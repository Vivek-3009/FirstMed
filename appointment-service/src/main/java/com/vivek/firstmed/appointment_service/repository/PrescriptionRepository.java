package com.vivek.firstmed.appointment_service.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    
    @Query(value = "SELECT prescription_id FROM prescriptions ORDER BY prescription_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopPrescriptionIdByOrderByPrescriptionIdDescIncludingDeleted();

    Page<Prescription> findByApointmentPatientId(String patientId, Pageable pageable);
    Page<Prescription> findByApointmentDoctorId(String doctorId, Pageable pageable);
    Page<Prescription> findByApointmentAppointmentDate(LocalDate appointmentDate, Pageable pageable);
    Page<PrescriptionDto> findByApointmentDoctorIdAndAppointmentAppointmentDate(String doctorId, LocalDate appointmentDate, Pageable pageable);
    Page<Prescription> findByApointmentPatientIdAndAppointmentAppointmentDate(String patientId,
            LocalDate appointmentDate, Pageable pageable);
    Page<Prescription> findByApointmentAppointmentId(String appointmentId, Pageable pageable);
    
    
}
