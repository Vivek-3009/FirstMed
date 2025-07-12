package com.vivek.firstmed.appointment_service.controller;

import static com.vivek.firstmed.appointment_service.util.ValidationUtils.validAppointmentId;

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

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.RescheduleAppointmentDto;
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
                appointmentDto.setAppointmentId(appointmentId);
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

        @Operation(summary = "Get all doctors")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointment retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No appointment found")
        })
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAllAppointment() {
                List<AppointmentDto> appointments = appointmentService.getAllAppointments();
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "All appointments retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by patient ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid patient ID format"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the patient")
        })
        @GetMapping("/patient/{patientId}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByPatientId(
                        @PathVariable String patientId) {
                validAppointmentId(patientId);
                List<AppointmentDto> appointments = appointmentService.getAppointmentByPatientId(patientId);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for patient retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by doctor ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid doctor ID format"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the doctor")
        })
        @GetMapping("/doctor/{doctorId}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByDoctorId(
                        @PathVariable String doctorId) {
                validAppointmentId(doctorId);
                List<AppointmentDto> appointments = appointmentService.getAppointmentByDoctorId(doctorId);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for doctor retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by date")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the date")
        })
        @GetMapping("/date/{date}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByDate(
                        @PathVariable String date) {
                List<AppointmentDto> appointments = appointmentService.getAppointmentByDate(date);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for date retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by status")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the status")
        })
        @GetMapping("/status/{status}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByStatus(
                        @PathVariable String status) {
                List<AppointmentDto> appointments = appointmentService.getAppointmentByStatus(status);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for status retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by doctor ID and date")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid doctor ID format or date format"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the doctor and date")
        })
        @GetMapping("/doctor/{doctorId}/date/{date}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByDoctorAndDate(
                        @PathVariable String doctorId, @PathVariable String date) {
                validAppointmentId(doctorId);
                List<AppointmentDto> appointments = appointmentService.getAppointmentByDoctorAndDate(doctorId, date);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for doctor and date retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get appointments by patient ID and date")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid patient ID format or date format"),
                        @ApiResponse(responseCode = "404", description = "No appointments found for the patient and date")
        })
        @GetMapping("/patient/{patientId}/date/{date}")
        public ResponseEntity<ServiceApiResponse<List<AppointmentDto>>> getAppointmentByPatientAndDate(
                        @PathVariable String patientId, @PathVariable String date) {
                validAppointmentId(patientId);
                List<AppointmentDto> appointments = appointmentService.getAppointmentByPatientAndDate(patientId, date);
                ServiceApiResponse<List<AppointmentDto>> response = new ServiceApiResponse<>(
                                "success",
                                "Appointments for patient and date retrieved successfully",
                                appointments);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Confirm an appointment")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Appointment confirmed successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid appointment ID format"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @PutMapping("/{appointmentId}/confirm")
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> confirmAppointment(@PathVariable String appointmentId) {
                validAppointmentId(appointmentId);
                AppointmentDto confirmedAppointment = appointmentService.confirmAppointment(appointmentId);
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment confirmed successfully",
                                confirmedAppointment);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Cancel an appointment")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid appointment ID format"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @PutMapping("/{appointmentId}/cancel")
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> cancelAppointment(@PathVariable String appointmentId) {
                validAppointmentId(appointmentId);
                AppointmentDto cancelledAppointment = appointmentService.cancelAppointment(appointmentId);
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment cancelled successfully",
                                cancelledAppointment);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Reschedule an appointment")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Appointment rescheduled successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid appointment ID format or date/time format"),
                        @ApiResponse(responseCode = "404", description = "Appointment not found")
        })
        @PutMapping("/{appointmentId}/reschedule")
        public ResponseEntity<ServiceApiResponse<AppointmentDto>> rescheduleAppointment(
                        @PathVariable String appointmentId,
                        @RequestBody AppointmentDto appointmentDto) {
                validAppointmentId(appointmentId);
                AppointmentDto rescheduledAppointment = appointmentService.rescheduleAppointment(appointmentDto );
                ServiceApiResponse<AppointmentDto> response = new ServiceApiResponse<>(
                                "success",
                                "Appointment rescheduled successfully",
                                rescheduledAppointment);
                return ResponseEntity.ok(response);
        }



}