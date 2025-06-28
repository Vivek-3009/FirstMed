package com.vivek.firstmed.patient_service.service;

import org.springframework.stereotype.Service;

import com.vivek.firstmed.patient_service.entity.HealthRecord;
import com.vivek.firstmed.patient_service.entity.Patient;
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
        String lastId = patientRepository.findTopByOrderByPatientIdDesc()
                .map(Patient::getPatientId)
                .orElse("P1000");

        if (!lastId.matches("^P\\d+$")) {
            throw new IllegalStateException("Invalid patient ID format: " + lastId);
        }

        int num = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("P%04d", num);
    }

    public String generateHealthRecordId(){
        String lastId = healthRecordRepository.findTopByOrderByHealthRecordIdDesc()
                .map(HealthRecord::getHealthRecordId)
                .orElse("PHR1000");
        
        if(!lastId.matches("^PHR\\d+$")) {
            throw new IllegalStateException("Invalid health record ID format: " + lastId);
        }

        int num = Integer.parseInt(lastId.substring(3)) + 1;
        return String.format("PHR%04d", num);
    }
}