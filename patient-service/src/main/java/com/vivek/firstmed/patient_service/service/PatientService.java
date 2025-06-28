package com.vivek.firstmed.patient_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vivek.firstmed.patient_service.dto.PatientDto;
import com.vivek.firstmed.patient_service.entity.Patient;
import com.vivek.firstmed.patient_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.patient_service.repository.PatientRepository;
import com.vivek.firstmed.patient_service.util.PatientMapperUtil;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapperUtil patientMapperUtil;
    private final IdGeneratorService idGeneratorService;

    public PatientService(
            PatientRepository patientRepository,
            PatientMapperUtil patientMapperUtil,
            IdGeneratorService idGeneratorService) {
        this.patientRepository = patientRepository;
        this.patientMapperUtil = patientMapperUtil;
        this.idGeneratorService = idGeneratorService;
    }

    public PatientDto createPatient(PatientDto patientDto) {
        String newId = idGeneratorService.generatePatientId();
        patientDto.setPatientId(newId);
        Patient patient = patientMapperUtil.dtoToEntity(patientDto);
        Patient saved = patientRepository.save(patient);
        return patientMapperUtil.entityToDto(saved);
    }

    public PatientDto getPatientById(String patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.map(patientMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + patientId));
    }

    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapperUtil::entityToDto)
                .toList();
    }

    public PatientDto updatePatient(String patientId, PatientDto patientDto) {
        return patientRepository.findById(patientId).map(
                existingPatient -> {
                    existingPatient.setFirstName(patientDto.getFirstName());
                    existingPatient.setLastName(patientDto.getLastName());
                    existingPatient.setGender(patientDto.getGender());
                    existingPatient.setDateOfBirth(patientDto.getDateOfBirth());
                    existingPatient.setPhoneNumber(patientDto.getPhoneNumber());
                    existingPatient.setEmail(patientDto.getEmail());
                    existingPatient.setAddress(patientDto.getAddress());
                    Patient updated = patientRepository.save(existingPatient);
                    return patientMapperUtil.entityToDto(updated);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + patientId));
    }

    public void deletePatient(String patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with ID: " + patientId);
        }
        patientRepository.deleteById(patientId);
    }

    public PatientDto addFamilyMember(String primaryPatientId, PatientDto familyMemberDto) {
        Patient primaryPatient = patientRepository.findById(primaryPatientId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Primary patient not found with ID: " + primaryPatientId));
        Patient familyMember = patientMapperUtil.dtoToEntity(familyMemberDto);
        familyMember.setPrimaryPatient(primaryPatient);
        Patient savedFamilyMember = patientRepository.save(familyMember);
        if (primaryPatient.getFamilyMembers() == null) {
            primaryPatient.setFamilyMembers(new ArrayList<>());
        }
        primaryPatient.getFamilyMembers().add(savedFamilyMember);
        patientRepository.save(primaryPatient);
        return patientMapperUtil.entityToDto(savedFamilyMember);
    }

    public void removeFamilyMember(String primaryPatientId, String familyMemberId) {
        Patient primaryPatient = patientRepository.findById(primaryPatientId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Primary patient not found with ID: " + primaryPatientId));

        Patient familyMember = primaryPatient.getFamilyMembers()
                .stream()
                .filter(member -> familyMemberId.equals(member.getPatientId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Family member not found with ID: " + familyMemberId));

        primaryPatient.getFamilyMembers().remove(familyMember);
        patientRepository.save(primaryPatient);
        patientRepository.deleteById(familyMemberId);
    }

}