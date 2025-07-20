package com.vivek.firstmed.appointment_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;

public interface PrescriptionService {
    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);
    PrescriptionDto getPrescriptionById(String prescriptionId);
    PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto);
    void deletePrescription(String prescriptionId);
    Page<PrescriptionDto> getAllPrescriptions(Pageable pageable);
    Page<PrescriptionDto> getPrescriptionsByPatientId(Pageable pageable, String patientId);
    Page<PrescriptionDto> getPrescriptionsByDoctorId(Pageable pageable, String doctorId);
    Page<PrescriptionDto> getPrescriptionsByDate(Pageable pageable, String date);
    Page<PrescriptionDto> getPrescriptionsByDoctorAndDate(Pageable pageable, String doctorId, String date);
    Page<PrescriptionDto> getPrescriptionsByPatientAndDate(Pageable pageable, String patientId, String date);
    Page<PrescriptionDto> getPrescriptionsByAppointmentId(Pageable pageable, String appointmentId);
    
}
