package com.vivek.firstmed.appointment_service.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.appointment_service.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    
    Optional<Appointment> findTopByOrderyAppointmentIdDesc();

    Optional<Appointment> findByPatientPatientId(String patientId);

    Optional<Appointment> findByDocotorDocotorId(String doctorId);

    Optional<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    // Optional<Appointment> findByAppointmentDate(String appointmentId);
    
}
