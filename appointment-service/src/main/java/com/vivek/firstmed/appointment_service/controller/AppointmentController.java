package com.vivek.firstmed.appointment_service.controller;

import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validAppointmentId;

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

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.ServiceApiResponse;
import com.vivek.firstmed.appointment_service.service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "Appointment management APIs")
@Validated
public class AppointmentController {

        private final AppointmentService appointmentService;

        public AppointmentController(AppointmentService appointmentService) {
                this.appointmentService = appointmentService;
        }

        @Operation(summary = "Create a new appointment")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Appointement created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        @PostMapping
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> createAppointment(
                        @Valid @RequestBody AppointmentDto appointmentDto) {
                AppointmentDto createdAppointment = appointmentService.createAppointment(appointmentDto);
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment created successfully",
                                createdAppointment);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Get appointment by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid appointment ID format"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @GetMapping("/{appointmentId}")
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> getAppointmentById(
                        @PathVariable String appointmentId) {
                validAppointmentId(appointmentId);
                AppointmentDto appointment = appointmentService.getAppointmentById(appointmentId);
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment retrieved successfully",
                                appointment);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Update an existing appointment")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @PutMapping("/{appointmentId}")
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> updateAppointment(@PathVariable String appointmentId,
                        @Valid @RequestBody AppointmentDto appointmentDto) {
                validAppointmentId(appointmentId);
                AppointmentDto updatedAppointment = appointmentService.updateAppointment(appointmentDto);
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment updated successfully",
                                updatedAppointment);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Delete a appointment by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Appoinment deleted successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid appointment ID format"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @DeleteMapping("/{appointmentId}")
        public ResponseEntity<ServiceApiResponse<Void>> deleteAppointment(@PathVariable String appointmentId) {
                validAppointmentId(appointmentId);
                appointmentService.deleteAppointment(appointmentId);
                ServiceApiResponse<Void> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment deleted successfully",
                                null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        

}