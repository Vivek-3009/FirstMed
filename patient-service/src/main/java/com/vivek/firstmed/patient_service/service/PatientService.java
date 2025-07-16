package com.vivek.firstmed.patient_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.patient_service.dto.PatientDto;

public interface PatientService {
    
    public PatientDto createPatient(PatientDto patientDto);
    public PatientDto getPatientById(String patientId);
    public Page<PatientDto> getAllPatients(Pageable pageable);
    public PatientDto updatePatient(PatientDto patientDto);
    public void deletePatient(String patientId);
    public PatientDto addFamilyMember(String primaryPatientId, PatientDto familyMemberDto);
    public void removeFamilyMember(String primaryPatientId, String familyMemberId);
}
