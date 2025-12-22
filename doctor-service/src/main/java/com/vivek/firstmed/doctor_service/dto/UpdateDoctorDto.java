package com.vivek.firstmed.doctor_service.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vivek.firstmed.doctor_service.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorDto {

    @Size(min = 5, max = 10, message = "Doctor ID must be between 5 and 10 characters")
    @Pattern(regexp = "^D\\d{4,}$", message = "Doctor ID must start with 'D' followed by at least 4 digits")
    private String doctorId;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(\\+91)?[6-9]\\d{9}$", message = "Phone number must be a valid Indian number")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String specialization;

    private String address;
}