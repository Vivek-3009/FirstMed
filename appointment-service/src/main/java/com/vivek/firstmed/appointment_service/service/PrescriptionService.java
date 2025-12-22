package com.vivek.firstmed.appointment_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.dto.UpdatePrescriptionDto;

public interface PrescriptionService {
    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);
    PrescriptionDto getPrescriptionById(String prescriptionId);
    PrescriptionDto updatePrescription(UpdatePrescriptionDto updatePrescriptionDto);
    void deletePrescription(String prescriptionId);
    Page<PrescriptionDto> getAllPrescriptions(Pageable pageable);
    Page<PrescriptionDto> getPrescriptionsByPatientId(Pageable pageable, String patientId);
    Page<PrescriptionDto> getPrescriptionsByDoctorId(Pageable pageable, String doctorId);
    Page<PrescriptionDto> getPrescriptionsByAppointmentDate(Pageable pageable, LocalDate appointmentDate);
    Page<PrescriptionDto> getPrescriptionsByDoctorAndApointmentDate(Pageable pageable, String doctorId, LocalDate apointmentDate);
    Page<PrescriptionDto> getPrescriptionsByPatientAndApointmentDate(Pageable pageable, String patientId, LocalDate apointmentDate);
    Page<PrescriptionDto> getPrescriptionsByAppointmentId(Pageable pageable, String appointmentId);
    
}
