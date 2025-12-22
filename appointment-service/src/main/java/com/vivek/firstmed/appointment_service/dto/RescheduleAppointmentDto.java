package com.vivek.firstmed.appointment_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RescheduleAppointmentDto {
    @Size(min = 5, max = 10, message = "Appointment ID must be between 5 and 10 characters")
    @Pattern(regexp = "^A\\d{4,}$", message = "Appointment ID must start with 'D' followed by at least 4 digits")
    @NotNull(message = "Appointment Id is required")
    private String appointmentId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date must be today or in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate newAppointmentDate;

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime newStartTime;

    
    private LocalTime newEndTime = newStartTime.plusMinutes(15);

    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        if (newStartTime == null || newEndTime == null) {
            return true;
        }
        return newEndTime.isAfter(newStartTime);
    }

    @AssertTrue(message = "Appointment time must be in the future if the appointment is today")
    public boolean isTimeValidForToday() {
        if (newAppointmentDate == null || newStartTime == null) {
            return true;
        }
        if (newAppointmentDate.isEqual(LocalDate.now())) {
            return newStartTime.isAfter(LocalTime.now());
        }
        return true;
    }


}
