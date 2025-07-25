package com.vivek.firstmed.appointment_service.controller;

import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validAppointmentId;
import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validDoctorId;
import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validPrescriptionId;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.dto.ServiceApiResponse;
import com.vivek.firstmed.appointment_service.dto.UpdatePrescriptionDto;
import com.vivek.firstmed.appointment_service.service.PrescriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prescription")
@Tag(name = "Prescription", description = "Prescription management APIs")
@Validated
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Operation(summary = "Create a new prescription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prescription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ServiceApiResponse<PrescriptionDto>> createPrescription(
            @Valid @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto createdPrescription = prescriptionService.createPrescription(prescriptionDto);
        ServiceApiResponse<PrescriptionDto> response = new ServiceApiResponse<>(
                "success",
                "Prescription created successfully",
                createdPrescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get prescription by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescription retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Prescription ID format"),
            @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<ServiceApiResponse<PrescriptionDto>> getPrescriptionById(
            @PathVariable String prescriptionId) {
        validPrescriptionId(prescriptionId);
        PrescriptionDto prescription = prescriptionService.getPrescriptionById(prescriptionId);
        ServiceApiResponse<PrescriptionDto> response = new ServiceApiResponse<>(
                "success",
                "Prescription retrieved successfully",
                prescription);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing prescription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescription updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<ServiceApiResponse<PrescriptionDto>> updatePrescription(@PathVariable String prescriptionId,
            @Valid @RequestBody UpdatePrescriptionDto updatePrescriptionDto) {
        validPrescriptionId(prescriptionId);
        updatePrescriptionDto.setPrescriptionId(prescriptionId);
        PrescriptionDto updatedPrescription = prescriptionService.updatePrescription(updatePrescriptionDto);
        ServiceApiResponse<PrescriptionDto> response = new ServiceApiResponse<>(
                "success",
                "Prescription updated successfully",
                updatedPrescription);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a prescription by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prescription deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Prescription ID format"),
            @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<ServiceApiResponse<Void>> deletePrescription(@PathVariable String prescriptionId) {
        validPrescriptionId(prescriptionId);
        prescriptionService.deletePrescription(prescriptionId);
        ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                "success",
                "Prescription deleted successfully",
                null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found")
    })
    @GetMapping
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getAllPrescriptions(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions(pageable);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by patient ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the patient")
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByPatientId(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String patientId) {
        validPrescriptionId(patientId);
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByPatientId(pageable, patientId);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by doctor ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the doctor")
    })
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByDoctorId(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String doctorId) {
        validDoctorId(doctorId);
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByDoctorId(pageable, doctorId);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by appointment date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the appointment date")
    })
    @GetMapping("/appointment-date/{appointmentDate}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByAppointmentDate(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable LocalDate appointmentDate) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByAppointmentDate(pageable,
                appointmentDate);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by doctor and appointment date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the doctor and appointment date")
    })
    @GetMapping("/doctor/{doctorId}/appointment-date/{appointmentDate}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByDoctorAndApointmentDate(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String doctorId, @PathVariable LocalDate appointmentDate) {
        validDoctorId(doctorId);
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByDoctorAndApointmentDate(pageable,
                doctorId, appointmentDate);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by patient and appointment date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the patient and appointment date")
    })
    @GetMapping("/patient/{patientId}/appointment-date/{appointmentDate}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByPatientAndApointmentDate(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String patientId, @PathVariable LocalDate appointmentDate) {
        validPrescriptionId(patientId);
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByPatientAndApointmentDate(pageable,
                patientId, appointmentDate);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all prescriptions by appointment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the appointment ID")
    })
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ServiceApiResponse<Page<PrescriptionDto>>> getPrescriptionsByAppointmentId(
            @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = "prescriptionId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String appointmentId) {
        validAppointmentId(appointmentId);
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByAppointmentId(pageable, appointmentId);
        ServiceApiResponse<Page<PrescriptionDto>> response = new ServiceApiResponse<>(
                "success",
                "Prescriptions retrieved successfully",
                prescriptions);
        return ResponseEntity.ok(response);
    }
}
