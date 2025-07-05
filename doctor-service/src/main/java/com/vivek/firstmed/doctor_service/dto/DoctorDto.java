package com.vivek.firstmed.doctor_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorDto {

    @Size(min = 5, max = 10, message = "Patient ID must be between 5 and 10 characters")
    @Pattern(regexp = "^D\\d{4,}$", message = "Patient ID must start with 'D' followed by at least 4 digits")
    private String doctorId;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Gender is required")
    private String gender;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^(\\+91)?[6-9]\\d{9}$",
        message = "Phone number must be a valid Indian number"
    )
    private String phoneNumber;
    
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Address is required")
    private String address;
}