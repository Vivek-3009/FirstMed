package com.vivek.firstmed.patient_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientDto {
    private String patientId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String address;
}