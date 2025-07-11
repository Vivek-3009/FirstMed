package com.vivek.firstmed.patient_service.controller;

import static com.vivek.firstmed.patient_service.util.ValidationUtils.validateHealthRecordId;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;
import com.vivek.firstmed.patient_service.dto.ServiceApiResponse;
import com.vivek.firstmed.patient_service.service.HealthRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/health-records")
@Tag(name = "Health Record", description = "Health Records management APIs")
@Validated
public class PatientHealthRecordController {

    private final HealthRecordService healthRecordService;
    
    public PatientHealthRecordController(HealthRecordService healthRecordService) {
        this.healthRecordService = healthRecordService;
    }
    
    @Operation(summary = "Create a new health record for a patient")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201", description = "Health record created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ServiceApiResponse.class))),
    })
    @PostMapping
    public ResponseEntity<ServiceApiResponse<HealthRecordDto>> createHealthRecord(@Valid @RequestBody HealthRecordDto healthRecordDto) {
        HealthRecordDto createdRecord = healthRecordService.createHealthRecord(healthRecordDto);
        ServiceApiResponse<HealthRecordDto> response = new ServiceApiResponse<>(
                "success",
                "Health record created successfully",
                createdRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get health record by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Health record retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid health record ID format"),
        @ApiResponse(responseCode = "404", description = "Health record not found")
    })
    @GetMapping("/{healthRecordId}")
    public ResponseEntity<ServiceApiResponse<HealthRecordDto>> getHealthRecordById(@PathVariable String healthRecordId) {
        validateHealthRecordId(healthRecordId);
        HealthRecordDto healthRecord = healthRecordService.getHealthRecordById(healthRecordId);
        ServiceApiResponse<HealthRecordDto> response = new ServiceApiResponse<>(
                "success",
                "Health record retrieved successfully",
                healthRecord);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing health record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Health record updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or health record ID format"),
        @ApiResponse(responseCode = "404", description = "Health record not found")
    })
    @PutMapping("/{healthRecordId}")
    public ResponseEntity<ServiceApiResponse<HealthRecordDto>> updateHealthRecord(@PathVariable String healthRecordId,
            @Valid @RequestBody HealthRecordDto healthRecordDto) {   
        validateHealthRecordId(healthRecordId);
        healthRecordDto.setHealthRecordId(healthRecordId);
        HealthRecordDto updatedRecord = healthRecordService.updateHealthRecord(healthRecordDto);
        ServiceApiResponse<HealthRecordDto> response = new ServiceApiResponse<>(
                "success",
                "Health record updated successfully",
                updatedRecord);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a health record by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Health record deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid health record ID format"),
        @ApiResponse(responseCode = "404", description = "Health record not found")
    })
    @DeleteMapping("/{healthRecordId}")
    public ResponseEntity<ServiceApiResponse<Void>> deleteHealthRecord(@PathVariable String healthRecordId) {
        validateHealthRecordId(healthRecordId);
        healthRecordService.deleteHealthRecord(healthRecordId);
        ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                "success",
                "Health record deleted successfully",
                null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all health records")
    @ApiResponse(responseCode = "200", description = "List of all health records")
    @GetMapping
    public ResponseEntity<ServiceApiResponse<List<HealthRecordDto>>> getAllHealthRecords() {
        List<HealthRecordDto> healthRecords = healthRecordService.getAllHealthRecords();
        ServiceApiResponse<List<HealthRecordDto>> response = new ServiceApiResponse<>(
                "success",
                "All health records retrieved successfully",
                healthRecords);
        return ResponseEntity.ok(response);
    }
        
    
}
