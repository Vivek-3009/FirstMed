package com.vivek.firstmed.doctor_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DoctorDto {
    private String doctorId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String specialization;
    private String address;
}