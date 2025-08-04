package com.vivek.firstmed.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vivek.firstmed.appointment_service.dto.PatientDto;

@FeignClient(name = "patient-service", url = "${patient.service.url}")
public interface PatientClient {
    @GetMapping("/internal/patient/{patientId}")
    PatientDto getPatientById(@PathVariable String patientId);
}
