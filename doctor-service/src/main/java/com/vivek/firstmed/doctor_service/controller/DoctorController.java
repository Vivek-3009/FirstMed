package com.vivek.firstmed.doctor_service.controller;

import static com.vivek.firstmed.doctor_service.util.ValidationUtils.validateDoctorId;

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

import com.vivek.firstmed.doctor_service.dto.DoctorDto;
import com.vivek.firstmed.doctor_service.dto.ServiceApiResponse;
import com.vivek.firstmed.doctor_service.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctor", description = "Doctor management APIs")
@Validated
public class DoctorController {

        private final DoctorService doctorService;

        public DoctorController(DoctorService doctorService) {
                this.doctorService = doctorService;
        }

        @Operation(summary = "Create a new doctor")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        @PostMapping
        public ResponseEntity<ServiceApiResponse<DoctorDto>> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
                DoctorDto createdDoctor = doctorService.addDoctor(doctorDto);
                ServiceApiResponse<DoctorDto> response = new ServiceApiResponse<>(
                                "success",
                                "Doctor created successfully",
                                createdDoctor);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Get doctor by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Doctor retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid doctor ID format"),
                        @ApiResponse(responseCode = "404", description = "Doctor not found")
        })
        @GetMapping("/{doctorId}")
        public ResponseEntity<ServiceApiResponse<DoctorDto>> getDoctorById(@PathVariable String doctorId) {
                validateDoctorId(doctorId);
                DoctorDto doctorDto = doctorService.getDoctorById(doctorId);
                ServiceApiResponse<DoctorDto> response = new ServiceApiResponse<>(
                                "success",
                                "Doctor retrieved successfully",
                                doctorDto);

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Update an existing doctor")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input"),
                        @ApiResponse(responseCode = "404", description = "Doctor not found")
        })
        @PutMapping("/{doctorId}")
        public ResponseEntity<ServiceApiResponse<DoctorDto>> updateDoctor(
                        @PathVariable String doctorId,
                        @Valid @RequestBody DoctorDto doctorDto) {
                validateDoctorId(doctorId);
                doctorDto.setDoctorId(doctorId);
                DoctorDto updatedDoctor = doctorService.updateDoctor(doctorDto);
                ServiceApiResponse<DoctorDto> response = new ServiceApiResponse<>(
                                "success",
                                "Doctor updated successfully",
                                updatedDoctor);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Delete a doctor by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid doctor ID format"),
                        @ApiResponse(responseCode = "404", description = "Doctor not found")
        })
        @DeleteMapping("/{doctorId}")
        public ResponseEntity<ServiceApiResponse<Void>> deleteDoctor(@PathVariable String doctorId) {
                validateDoctorId(doctorId);
                doctorService.deleteDoctor(doctorId);
                ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                                "success",
                                "Doctor deleted successfully",
                                null);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get all doctors")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of doctors retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No doctors found")
        })
        @GetMapping
        public ResponseEntity<ServiceApiResponse<Page<DoctorDto>>> getAllDoctors(
                        @Parameter(hidden = true) 
                        @PageableDefault(size = 10, page = 0, sort = "doctorId", direction = Sort.Direction.DESC) Pageable pageable) {
                Page<DoctorDto> doctors = doctorService.getAllDoctors(pageable);
                ServiceApiResponse<Page<DoctorDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Doctors retrieved successfully",
                                doctors);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get doctors by specialization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No doctors found with the specified specialization")
        })
        @GetMapping("/specialization/{specialization}")
        public ResponseEntity<ServiceApiResponse<Page<DoctorDto>>> getDoctorsBySpecialization(
                        @Parameter(hidden = true) 
                        @PageableDefault(size = 10, page = 0, sort = "doctorId", direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable String specialization) {
                Page<DoctorDto> doctors = doctorService.getDoctorsBySpecialization(pageable, specialization);
                ServiceApiResponse<Page<DoctorDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Doctors retrieved successfully in specialization: " + specialization,
                                doctors);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get doctors by location")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No doctors found in the specified location")
        })
        @GetMapping("/location/{location}")
        public ResponseEntity<ServiceApiResponse<Page<DoctorDto>>> getDoctorsByLocation(
                        @Parameter(hidden = true) 
                        @PageableDefault(size = 10, page = 0, sort = "patientId", direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable String location) {
                Page<DoctorDto> doctors = doctorService.getDoctorsByLocation(pageable, location);
                ServiceApiResponse<Page<DoctorDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Doctors retrieved successfully in location: " + location,
                                doctors);
                return ResponseEntity.ok(response);
        }
}
