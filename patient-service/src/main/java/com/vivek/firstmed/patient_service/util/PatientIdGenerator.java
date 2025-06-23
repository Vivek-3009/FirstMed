package com.vivek.firstmed.patient_service.util;

import org.springframework.stereotype.Component;

import com.vivek.firstmed.patient_service.entity.Patient;
import com.vivek.firstmed.patient_service.repository.PatientRepository;

@Component
public class PatientIdGenerator {

    private PatientRepository patientRepository;

    public PatientIdGenerator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public String generatePatientId() {
        String lastId = patientRepository.findTopByOrderByPatientIdDesc()
                .map(Patient::getPatientId)
                .orElse("P1000");
        int num = Integer.parseInt(lastId.substring(1)) + 1;
        return "P" + num;
    }
}