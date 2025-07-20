package com.vivek.firstmed.patient_service.service;

import java.util.List;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;
import com.vivek.firstmed.patient_service.dto.UpdateHealthRecordDto;

public interface HealthRecordService {
    public HealthRecordDto createHealthRecord(HealthRecordDto healthRecordDto);
    public HealthRecordDto getHealthRecordById(String healthRecordId);
    public HealthRecordDto updateHealthRecord(UpdateHealthRecordDto updateHealthRecordDto);
    public List<HealthRecordDto> getAllHealthRecords();
    public void deleteHealthRecord(String healthRecordId);

}