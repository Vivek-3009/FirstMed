package com.vivek.firstmed.patient_service.service;

import org.springframework.stereotype.Service;

import com.vivek.firstmed.patient_service.repository.HealthRecordRepository;
import com.vivek.firstmed.patient_service.repository.PatientRepository;

@Service
public class IdGeneratorService {

    private PatientRepository patientRepository;
    private HealthRecordRepository healthRecordRepository;

    public IdGeneratorService(PatientRepository patientRepository,
            HealthRecordRepository healthRecordRepository) {
        this.patientRepository = patientRepository;
        this.healthRecordRepository = healthRecordRepository;
    }

    public String generatePatientId() {
        return patientRepository.findTopPatientIdsByOrderByPatientIdsDescIncludingDeleted()
                .map(lastId -> {
                    int numericPart = Integer.parseInt(lastId.substring(3));
                    return "P" + (numericPart + 1);
                })
                .orElse("P10001");
    }

    public String generateHealthRecordId() {
        return healthRecordRepository.findTopHealthRecordIdsByOrderByHealthRecordIdsDescIncludingDeleted()
                .map(lastId -> {
                    int numericPart = Integer.parseInt(lastId.substring(2));
                    return "HR" + (numericPart + 1);
                })
                .orElse("HR10001");
    }
}