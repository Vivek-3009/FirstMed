package com.vivek.firstmed.patient_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecordDto {

    @Size(min = 7, max = 10, message = "Health record ID must be between 7 and 10 characters")
    @Pattern(regexp = "^PHR\\d{4,}$", message = "Health record ID must start with 'PHR' followed by at least 4 digits")
    private String healthRecordId;

    @NotNull(message = "Patient ID is required")
    private String patientId;

    @NotBlank(message = "Blood type is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood type must be one of A+, A-, B+, B-, AB+, AB-, O+, or O-")
    private String bloodType;

    @Size(max = 255, message = "Allergies must not exceed 255 characters")
    private String allergies;

    @Size(max = 255, message = "Chronic diseases must not exceed 255 characters")
    private String chronicDiseases;

    @Size(max = 255, message = "Medications must not exceed 255 characters")
    private String medications;

    @Size(max = 1000, message = "Medical history must not exceed 1000 characters")
    private String medicalHistory;

    @PastOrPresent(message = "Last updated date must be in the past or present")
    private LocalDate lastUpdated;
}
