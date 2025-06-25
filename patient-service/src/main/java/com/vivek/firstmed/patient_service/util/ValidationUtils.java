package com.vivek.firstmed.patient_service.util;

import com.vivek.firstmed.patient_service.exception.BadRequestException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validatePatientId(String patientId) {
        if (!patientId.matches("^P[1-9][0-9]{3,}$")) {
            throw new BadRequestException("Invalid Patient ID format. Must match: P followed by at least 4 digits (e.g., P1000)");
        }
    }
}
