package com.vivek.firstmed.patient_service.service;

import java.util.List;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;
import com.vivek.firstmed.patient_service.entity.HealthRecord;
import com.vivek.firstmed.patient_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.patient_service.repository.HealthRecordRepository;
import com.vivek.firstmed.patient_service.util.HealthRecordMapperUtil;

public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final HealthRecordMapperUtil healthRecordMapperUtil;
    private final IdGeneratorService idGeneratorService;

    public HealthRecordService(
            HealthRecordRepository healthRecordRepository,
            HealthRecordMapperUtil healthRecordMapperUtil,
            IdGeneratorService idGeneratorService) {
        this.healthRecordRepository = healthRecordRepository;
        this.healthRecordMapperUtil = healthRecordMapperUtil;
        this.idGeneratorService = idGeneratorService;
    }

    public HealthRecordDto createHealthRecord(HealthRecordDto healthRecordDto) {
        String newId = idGeneratorService.generateHealthRecordId();
        healthRecordDto.setHealthRecordId(newId);
        HealthRecord healthRecord = healthRecordMapperUtil.dtoToEntity(healthRecordDto);
        HealthRecord saved = healthRecordRepository.save(healthRecord);
        return healthRecordMapperUtil.entityToDto(saved);
    }

    public HealthRecordDto getHealthRecordById(String healthRecordId) {
        return healthRecordRepository.findById(healthRecordId)
                .map(healthRecordMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Health record not found with ID: " + healthRecordId));
    }

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

    public void deleteHealthRecord(String healthRecordId) {
        if (!healthRecordRepository.existsById(healthRecordId)) {
            throw new ResourceNotFoundException("Health record not found with ID: " + healthRecordId);
        }
        healthRecordRepository.deleteById(healthRecordId);
    }

    public List<HealthRecordDto> getAllHealthRecords() {
        return healthRecordRepository.findAll()
                .stream()
                .map(healthRecordMapperUtil::entityToDto)
                .toList();
    }

}
