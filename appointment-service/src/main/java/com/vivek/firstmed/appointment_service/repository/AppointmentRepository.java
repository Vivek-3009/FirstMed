package com.vivek.firstmed.appointment_service.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vivek.firstmed.appointment_service.entity.Appointment;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query(value = "SELECT appointment_id FROM appointments ORDER BY appointment_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findTopAppointmentIdByOrderByAppointmentIdDescIncludingDeleted();

    Page<Appointment> findByPatientId(String patientId, Pageable pageable);

    Page<Appointment> findByDocotorId(String doctorId, Pageable pageable);

    Page<Appointment> findByAppointmentDate(LocalDate appointmentDate, Pageable pageable);

    Page<Appointment> findByStatus(AppointmentStatus appointmentStatus, Pageable pageable);

    Page<Appointment> findByDocotorIdAndAppointmentDate(String doctorId, LocalDate appointmentDate, Pageable pageable);

    Page<Appointment> findByPatientIdAndAppointmentDate(String patientId, LocalDate appointmentDate, Pageable pageable);
    
}
