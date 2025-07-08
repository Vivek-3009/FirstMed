package com.vivek.firstmed.patient_service.controller;

import static com.vivek.firstmed.patient_service.util.ValidationUtils.validatePatientId;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.vivek.firstmed.patient_service.dto.ServiceApiResponse;
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
                        @ApiResponse(responseCode = "201", description = "Patient created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ServiceApiResponse.class)))
        })
        @PostMapping
        public ResponseEntity<ServiceApiResponse<PatientDto>> createPatient(@Valid @RequestBody PatientDto patientDto) {
                PatientDto createdPatient = patientService.createPatient(patientDto);
                ServiceApiResponse<PatientDto> response = new ServiceApiResponse<>(
                                "success",
                                "Patient created successfully",
                                createdPatient);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Get patient by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Patient found"),
                        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
                        @ApiResponse(responseCode = "404", description = "Patient not found")
        })
        @GetMapping("/{patientId}")
        public ResponseEntity<ServiceApiResponse<PatientDto>> getPatientById(@PathVariable String patientId) {
                validatePatientId(patientId);
                PatientDto patient = patientService.getPatientById(patientId);
                ServiceApiResponse<PatientDto> response = new ServiceApiResponse<>(
                                "success",
                                "Patient found",
                                patient);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Update patient by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
                        @ApiResponse(responseCode = "404", description = "Patient not found")
        })
        @PutMapping
        public ResponseEntity<ServiceApiResponse<PatientDto>> updatePatient(
                        @Valid @RequestBody PatientDto patientDto) {
                PatientDto updated = patientService.updatePatient(patientDto);
                ServiceApiResponse<PatientDto> response = new ServiceApiResponse<>(
                                "success",
                                "Patient updated successfully",
                                updated);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Delete patient by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid Patient ID format"),
                        @ApiResponse(responseCode = "404", description = "Patient not found")
        })
        @DeleteMapping("/{patientId}")
        public ResponseEntity<ServiceApiResponse<Void>> deletePatient(@PathVariable String patientId) {
                validatePatientId(patientId);
                patientService.deletePatient(patientId);
                ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                                "success",
                                "Patient deleted successfully",
                                null);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get all patients")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No patients found")
        })
        @GetMapping
        public ResponseEntity<ServiceApiResponse<List<PatientDto>>> getAllPatients() {
                List<PatientDto> patients = patientService.getAllPatients();
                ServiceApiResponse<List<PatientDto>> response = new ServiceApiResponse<>(
                                "success",
                                "All patients fetched",
                                patients);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Add a new family member to a patient")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Family member added successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ServiceApiResponse.class)))
        })
        @PostMapping("/{patientId}/family")
        public ResponseEntity<ServiceApiResponse<PatientDto>> addFamilyMember(
                        @PathVariable("patientId") String primaryPatientId,
                        @Valid @RequestBody PatientDto familyMemberDto) {
                PatientDto createdPatient = patientService.addFamilyMember(primaryPatientId, familyMemberDto);
                ServiceApiResponse<PatientDto> response = new ServiceApiResponse<>(
                                "success",
                                "Family member added successfully",
                                createdPatient);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Remove patient family member by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Family member removed successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid Family member ID format"),
                        @ApiResponse(responseCode = "404", description = "Patient or Family member not found")
        })
        @DeleteMapping("/{patientId}/family/{familyMemberId}")
        public ResponseEntity<ServiceApiResponse<Void>> removePatientFamilyMember(@PathVariable("patientId") String primaryPatientId,
                        @PathVariable String familyMemberId) {
                validatePatientId(primaryPatientId);
                validatePatientId(familyMemberId);
                patientService.removeFamilyMember(primaryPatientId, familyMemberId);
                ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                                "success",
                                "Family member removed successfully",
                                null);
                return ResponseEntity.ok(response);
        }
}
