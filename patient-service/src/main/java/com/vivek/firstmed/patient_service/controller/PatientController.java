package com.vivek.firstmed.patient_service.controller;

import static com.vivek.firstmed.patient_service.util.ValidationUtils.validatePatientId;

import java.util.List;

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
import com.vivek.firstmed.patient_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.patient_service.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient", description = "Patient management APIs")
@Validated
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Create a new patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = PatientDto.class)))
    })
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
        PatientDto created = patientService.createPatient(patientDto);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Get patient by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient found"),
        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable String patientId) {
        validatePatientId(patientId);
        PatientDto patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return ResponseEntity.ok(patient);
    }

    @Operation(summary = "Get all patients")
    @ApiResponse(responseCode = "200", description = "List of patients")
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @Operation(summary = "Update patient by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable String patientId,
            @Valid @RequestBody PatientDto patientDto) {
        validatePatientId(patientId);
        PatientDto updated = patientService.updatePatient(patientId, patientDto);
        if (updated == null) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete patient by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable String patientId) {
        validatePatientId(patientId);
        PatientDto patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
