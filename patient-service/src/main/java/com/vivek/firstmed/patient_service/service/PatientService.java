package com.vivek.firstmed.patient_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vivek.firstmed.patient_service.dto.PatientDto;
import com.vivek.firstmed.patient_service.entity.Patient;
import com.vivek.firstmed.patient_service.repository.PatientRepository;
import com.vivek.firstmed.patient_service.util.PatientIdGenerator;
import com.vivek.firstmed.patient_service.util.PatientMapperUtil;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private PatientMapperUtil patientMapperUtil;
    private PatientIdGenerator patientIdGenerator;

    public PatientService(
            PatientRepository patientRepository,
            PatientMapperUtil patientMapperUtil,
            PatientIdGenerator patientIdGenerator) {
        this.patientRepository = patientRepository;
        this.patientMapperUtil = patientMapperUtil;
        this.patientIdGenerator = patientIdGenerator;
    }

    public PatientDto createPatient(PatientDto patientDto) {
        String newId = patientIdGenerator.generatePatientId();
        patientDto.setPatientId(newId);
        Patient patient = patientMapperUtil.dtoToEntity(patientDto);
        Patient saved = patientRepository.save(patient);
        return patientMapperUtil.entityToDto(saved);
    }

    public PatientDto getPatientById(String patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.map(patientMapperUtil::entityToDto).orElse(null);
    }

    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapperUtil::entityToDto)
                .toList();
    }

    public PatientDto updatePatient(String patientId, PatientDto patientDto) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setFirstName(patientDto.getFirstName());
            patient.setLastName(patientDto.getLastName());
            patient.setGender(patientDto.getGender());
            patient.setDateOfBirth(patientDto.getDateOfBirth());
            patient.setPhoneNumber(patientDto.getPhoneNumber());
            patient.setEmail(patientDto.getEmail());
            patient.setAddress(patientDto.getAddress());
            Patient updated = patientRepository.save(patient);
            return patientMapperUtil.entityToDto(updated);
        }
        return null;
    }

    public void deletePatient(String patientId) {
        patientRepository.deleteById(patientId);
    }
}