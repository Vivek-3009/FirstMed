package com.vivek.firstmed.appointment_service.util;

import com.vivek.firstmed.appointment_service.exception.BadRequestException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validAppointmentId(String appointmentId) {
        if (!appointmentId.matches("^A[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Appointment ID format. Must match: A followed by at least 4 digits (e.g., P1000)");
        }
    }

    public static void validPrescriptionId(String prescriptionId) {
        if (!prescriptionId.matches("^PRC[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Prescription ID format. Must match: PRC followed by at least 4 digits (e.g., PRC1000)");
        }
    }    

    public static void validateDoctorId(String doctorId) {
        if (!doctorId.matches("^D[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Doctor ID format. Must match: D followed by at least 4 digits (e.g., P1000)");
        }
    }

    public static void validatePatientId(String patientId) {
        if (!patientId.matches("^P[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Patient ID format. Must match: P followed by at least 4 digits (e.g., P1000)");
        }
    }
}
