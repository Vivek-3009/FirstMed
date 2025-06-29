package com.vivek.firstmed.patient_service.service;

import java.util.List;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;

public inteface HealthRecordService {

    public HealthRecordDto createHealthRecord(HealthRecordDto healthRecordDto);
    public HealthRecordDto getHealthRecordByPatientId(String patientId)
    public List<HealthRecordDto> getAllHealthRecords();
    public HealthRecordDto updateHealthRecord(String patientId, HealthRecordDto healthRecordDto);
}