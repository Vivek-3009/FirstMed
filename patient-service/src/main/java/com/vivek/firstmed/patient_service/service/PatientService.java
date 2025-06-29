package com.vivek.firstmed.patient_service.service;

import java.util.List;

import com.vivek.firstmed.patient_service.dto.PatientDto;

public interface PatientService {
    
    public PatientDto createPatient(PatientDto patientDto);
    public PatientDto getPatientById(String patientId);
    public List<PatientDto> getAllPatients();
    public PatientDto updatePatient(String patientId, PatientDto patientDto);
}
