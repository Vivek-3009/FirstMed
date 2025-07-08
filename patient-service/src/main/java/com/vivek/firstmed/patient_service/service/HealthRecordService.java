package com.vivek.firstmed.patient_service.service;

import java.util.List;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;

public interface HealthRecordService {
    public HealthRecordDto createHealthRecord(HealthRecordDto healthRecordDto);
    public HealthRecordDto getHealthRecordById(String healthRecordId);
    public HealthRecordDto updateHealthRecord(HealthRecordDto healthRecordDto);
    public List<HealthRecordDto> getAllHealthRecords();
    public void deleteHealthRecord(String healthRecordId);

}