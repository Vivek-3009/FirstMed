package com.vivek.firstmed.appointment_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;

import lombok.Data;

@Data
public class AppointmentResponseDto {

    private String appointmentId;
    private PatientDto patientId;
    private DoctorDto doctorId;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    private PrescriptionDto prescription;
}
