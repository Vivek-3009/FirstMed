package com.vivek.firstmed.patient_service.util;

import com.vivek.firstmed.patient_service.exception.BadRequestException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validatePatientId(String patientId) {
        if (!patientId.matches("^P[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Patient ID format. Must match: P followed by at least 4 digits (e.g., P1000)");
        }
    }

    public static void validateHealthRecordId(String healthRecordId) {
        if (!healthRecordId.matches("^HR[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Health Record ID format. Must match: HR followed by at least 4 digits (e.g., HR1000)");
        }
    }
}
