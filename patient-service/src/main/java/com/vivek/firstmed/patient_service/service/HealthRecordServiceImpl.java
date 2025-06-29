package com.vivek.firstmed.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;
import com.vivek.firstmed.patient_service.entity.HealthRecord;
import com.vivek.firstmed.patient_service.entity.Patient;
import com.vivek.firstmed.patient_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.patient_service.repository.HealthRecordRepository;
import com.vivek.firstmed.patient_service.repository.PatientRepository;
import com.vivek.firstmed.patient_service.util.HealthRecordMapperUtil;

import org.springframework.transaction.annotation.Transactional;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final HealthRecordMapperUtil healthRecordMapperUtil;
    private final IdGeneratorService idGeneratorService;
    private final PatientRepository patientRepository;

    public HealthRecordService(
            HealthRecordRepository healthRecordRepository,
            HealthRecordMapperUtil healthRecordMapperUtil,
            IdGeneratorService idGeneratorService,
            PatientRepository patientRepository) {
        this.healthRecordRepository = healthRecordRepository;
        this.healthRecordMapperUtil = healthRecordMapperUtil;
        this.idGeneratorService = idGeneratorService;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public HealthRecordDto createHealthRecord(HealthRecordDto healthRecordDto) {
        String newId = idGeneratorService.generateHealthRecordId();
        healthRecordDto.setHealthRecordId(newId);
        Patient patient = patientRepository.findById(healthRecordDto.getPatientId())
        .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + healthRecordDto.getPatientId()));
        HealthRecord healthRecord = healthRecordMapperUtil.dtoToEntity(healthRecordDto);
        healthRecord.setPatient(patient);
        patient.setHealthRecord(healthRecord);
        return healthRecordMapperUtil.entityToDto(patient.getHealthRecord());
    }

    @Transactional(readOnly = true)
    public HealthRecordDto getHealthRecordById(String healthRecordId) {
        return healthRecordRepository.findById(healthRecordId)
                .map(healthRecordMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Health record not found with ID: " + healthRecordId));
    }

    @Transactional
    public HealthRecordDto updateHealthRecord(String healthRecordId, HealthRecordDto healthRecordDto) {
        return healthRecordRepository.findById(healthRecordId)
                .map(existingRecord -> {
                    existingRecord.setBloodType(healthRecordDto.getBloodType());
                    existingRecord.setAllergies(healthRecordDto.getAllergies());
                    existingRecord.setChronicDiseases(healthRecordDto.getChronicDiseases());
                    existingRecord.setMedications(healthRecordDto.getMedications());
                    existingRecord.setMedicalHistory(healthRecordDto.getMedicalHistory());
                    existingRecord.setLastUpdated(healthRecordDto.getLastUpdated());
                    HealthRecord updated = healthRecordRepository.save(existingRecord);
                    return healthRecordMapperUtil.entityToDto(updated);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Health record not found with ID: " + healthRecordId));
    }

    @Transactional
    public void deleteHealthRecord(String healthRecordId) {
        if (!healthRecordRepository.existsById(healthRecordId)) {
            throw new ResourceNotFoundException("Health record not found with ID: " + healthRecordId);
        }
        healthRecordRepository.deleteById(healthRecordId);
    }

    @Transactional(readOnly = true)
    public List<HealthRecordDto> getAllHealthRecords() {
        return healthRecordRepository.findAll()
                .stream()
                .map(healthRecordMapperUtil::entityToDto)
                .toList();
    }

}
