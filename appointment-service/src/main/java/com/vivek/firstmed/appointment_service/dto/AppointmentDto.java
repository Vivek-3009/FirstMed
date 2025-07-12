package com.vivek.firstmed.appointment_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppointmentDto {

    @Size(min = 5, max = 10, message = "Appointment ID must be between 5 and 10 characters")
    @Pattern(regexp = "^A\\d{4,}$", message = "Appointment ID must start with 'A' followed by at least 4 digits")
    private String appointmentId;

    @NotBlank(message = "Patient ID is required")
    @Size(min = 5, max = 10, message = "Patient ID must be between 5 and 10 characters")
    @Pattern(regexp = "^P\\d{4,}$", message = "Patient ID must start with 'P' followed by at least 4 digits")
    private String patientId;

    @NotBlank(message = "Doctor ID is required")
    @Size(min = 5, max = 10, message = "Doctor ID must be between 5 and 10 characters")
    @Pattern(regexp = "^D\\d{4,}$", message = "Doctor ID must start with 'D' followed by at least 4 digits")
    private String doctorId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date must be today or in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "HH:mm")
    @FutureOrPresent(message = "Start time must be today or in the future")
    private LocalTime startTime;

    private LocalTime endTime = startTime.plusMinutes(15);

    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Valid
    private PrescriptionDto prescription;

    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "Appointment time must be in the future if the appointment is today")
    public boolean isTimeValidForToday() {
        if (appointmentDate == null || startTime == null) {
            return true;
        }
        if (appointmentDate.isEqual(LocalDate.now())) {
            return startTime.isAfter(LocalTime.now());
        }
        return true;
    }

}
