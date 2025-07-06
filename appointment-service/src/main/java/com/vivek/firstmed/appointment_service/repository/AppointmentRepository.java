package com.vivek.firstmed.appointment_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.firstmed.appointment_service.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    
    Optional<Appointment> findTopByOrderByAppointmentIdDesc();
    
}
