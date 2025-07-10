package com.vivek.firstmed.appointment_service.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.appointment_service.entity.Appointment;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    
    Optional<Appointment> findTopByOrderByAppointmentIdDesc();

    Optional<Appointment> findByPatientId(String patientId);

    Optional<Appointment> findByDocotorId(String doctorId);

    Optional<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    Optional<Appointment> findByStatus(AppointmentStatus appointmentStatus);
    // Optional<Appointment> findByAppointmentDate(String appointmentId);

    Optional<Appointment> findByDocotorIdAndAppointmentDate(String doctorId, LocalDate appointmentDate);

    Optional<Appointment> findByPatientIdAndAppointmentDate(String patientId, LocalDate appointmentDate);
    
}
