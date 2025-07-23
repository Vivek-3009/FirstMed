package com.vivek.firstmed.appointment_service.controller;

import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validPrescriptionId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.dto.ServiceApiResponse;
import com.vivek.firstmed.appointment_service.service.PrescriptionService;

import io.swagger.v3.oas.annotations.Operation;
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

    
}
