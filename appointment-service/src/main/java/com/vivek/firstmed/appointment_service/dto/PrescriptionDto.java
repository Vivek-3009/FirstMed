package com.vivek.firstmed.appointment_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class PrescriptionDto {
    
    @Size(min = 5, max = 10, message = "Prescription ID must be between 5 and 10 characters")
    private String prescriptionId;

    @NotBlank(message = "Appointment ID is required")
    private String appointmentId;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @NotNull(message = "Medicines map cannot be null")
    @Size(min = 1, message = "At least one medicine is required")
    private Map<@NotBlank(message = "Medicine name is required") String,
                @NotBlank(message = "Dosage is required") String> medicines;

    @Size(max = 500, message = "Test recommendations must not exceed 500 characters")
    private String testRecommendations;

    @FutureOrPresent(message = "Follow-up date must be today or in the future")
    private LocalDate followUpDate;

    @PastOrPresent(message = "Issued date cannot be in the future")
    private LocalDate issuedAt;
}
