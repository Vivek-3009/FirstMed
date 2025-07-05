package com.vivek.firstmed.doctor_service.util;

import com.vivek.firstmed.doctor_service.exception.BadRequestException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validateDoctorId(String doctorId) {
        if (!doctorId.matches("^D[1-9]\\d{3,}$")) {
            throw new BadRequestException("Invalid Doctor ID format. Must match: D followed by at least 4 digits (e.g., P1000)");
        }
    }

    
}
