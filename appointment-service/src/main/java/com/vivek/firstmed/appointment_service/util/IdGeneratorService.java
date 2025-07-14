package com.vivek.firstmed.appointment_service.util;

import org.springframework.stereotype.Component;

import com.vivek.firstmed.appointment_service.repository.AppointmentRepository;
import com.vivek.firstmed.appointment_service.repository.PrescriptionRepository;

@Component
public class IdGeneratorService {

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;

    public IdGeneratorService(AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public String generateAppointmentId() {
        return appointmentRepository.findTopAppointmentIdByOrderByAppointmentIdDescIncludingDeleted()
                .map(lastId -> {
                    int numericPart = Integer.parseInt(lastId.substring(1));
                    return "A" + (numericPart + 1);
                })
                .orElse("A10001");
    }

    public String generatePrescriptionId() {
        return prescriptionRepository.findTopPrescriptionIdByOrderByPrescriptionIdDescIncludingDeleted()
                .map(lastId -> {
                    int numericPart = Integer.parseInt(lastId.substring(3));
                    return "PRC" + (numericPart + 1);
                })
                .orElse("PRC10001");
    }
}