package com.vivek.firstmed.patient_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.firstmed.patient_service.dto.PatientDto;
import com.vivek.firstmed.patient_service.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Validated @RequestBody PatientDto patientDto) {
        PatientDto created = patientService.createPatient(patientDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable String patientId) {
        PatientDto patient = patientService.getPatientById(patientId);
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable String patientId,
            @Validated @RequestBody PatientDto patientDto) {
        PatientDto updated = patientService.updatePatient(patientId, patientDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable String patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}