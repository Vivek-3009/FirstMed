package com.vivek.firstmed.appointment_service.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE appointments SET is_deleted = true WHERE appointment_id = ?")
@SQLRestriction("is_deleted = false")
public class Appointment {

    @Id
    @Column(length = 10)
    private String appointmentId;

    @Column(nullable = false, length = 10)
    private String patientId;

    @Column(nullable = false, length = 10)
    private String doctorId;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id", referencedColumnName = "prescription_id")
    @JsonManagedReference
    private Prescription prescription;

    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
