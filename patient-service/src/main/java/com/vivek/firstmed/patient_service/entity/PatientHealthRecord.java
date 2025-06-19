package com.vivek.firstmed.patient_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientHealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotBlank(message = "Blood type is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood type")
    private String bloodType;

    @Size(max = 255, message = "Allergies info too long")
    private String allergies;

    @Size(max = 255, message = "Chronic diseases info too long")
    private String chronicDiseases;

    @Size(max = 255, message = "Medications info too long")
    private String medications;

    @Size(max = 1000, message = "Medical history too long")
    private String medicalHistory;

    @PastOrPresent(message = "Last updated date must be today or in the past")
    private LocalDate lastUpdated = LocalDate.now();
}
