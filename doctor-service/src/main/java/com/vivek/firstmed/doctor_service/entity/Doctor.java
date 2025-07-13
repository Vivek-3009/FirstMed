package com.vivek.firstmed.doctor_service.entity;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE doctor SET is_deleted = true WHERE doctor_id = ?")
@SQLRestriction("is_deleted = false")
public class Doctor {

    @Id
    @Column(length = 10)
    private String doctorId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String specialization;

    @Column(nullable = false, length = 255)
    private String address;
    
    @Column(nullable = false)
    boolean isDeleted = false;
}