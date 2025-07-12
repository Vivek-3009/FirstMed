package com.vivek.firstmed.appointment_service.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "prescriptions")
public class Prescription {

    @PrePersist
    public void setIssuedAt() {
        this.issuedAt = LocalDate.now();
    }

    @Id
    @Column(name = "prescription_id", length = 10)
    private String prescriptionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    @JsonBackReference
    private Appointment appointment;

    @Column(name = "notes")
    private String notes;

    @ElementCollection
    @CollectionTable(name = "prescription_medicines", joinColumns = @JoinColumn(name = "prescription_id"))
    @MapKeyColumn(name = "medicine_name")
    @Column(name = "dosage")
    private Map<String, String> medicines;

    @ElementCollection
    @CollectionTable(name = "test_recommendations", joinColumns = @JoinColumn(name = "prescription_id"))
    @Column(name = "test_name")
    private List<String> testRecommendations;

    @Column(name = "follow_up_date")
    private LocalDate followUpDate;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
