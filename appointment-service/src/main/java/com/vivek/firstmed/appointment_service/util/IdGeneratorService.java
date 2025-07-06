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
        return appointmentRepository.findTopByOrderByAppointmentIdDesc()
                .map(appointment -> {
                    String lastId = appointment.getAppointmentId();
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return "A" + newId;
                })
                .orElse("A10001");
    }

    public String generatePrescriptionId() {
        return prescriptionRepository.findTopByOrderByPrescriptionIdDesc()
                .map(prescription -> {
                    String lastId = prescription.getPrescriptionId();
                    int newId = Integer.parseInt(lastId.substring(3)) + 1;
                    return "PRC" + newId;
                })
                .orElse("PRC10001");
    }
}