package com.vivek.firstmed.patient_service.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_health_records")
public class HealthRecord {

    @PrePersist
    public void setLastUpdated() {
        this.lastUpdated = LocalDate.now();
    }

    @Id
    @Column(length = 10)
    private String healthRecordId;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(length = 5)
    private String bloodType;

    @Column(length = 255)
    private String allergies;

    @Column(length = 255)
    private String chronicDiseases;

    @Column(length = 255)
    private String medications;

    @Column(length = 1000)
    private String medicalHistory;

    @Column(nullable = false)
    private LocalDate lastUpdated;

    @Column(nullable = false)
    boolean isDeleted = false;
}
