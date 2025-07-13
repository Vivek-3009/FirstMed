package com.vivek.firstmed.doctor_service.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vivek.firstmed.doctor_service.repository.DoctorRepository;

@Component
public class IdGeneratorService {

    private final DoctorRepository doctorRepository;

    public IdGeneratorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public String generateDoctorId() {
        return doctorRepository.findTopDoctorIdByOrderByDoctorIdDescIncludingDeleted()
        .map(lastId -> {
            int numericPart = Integer.parseInt(lastId.substring(1));
            return "D" + (numericPart + 1);
        })
        .orElse("D10001");
    }
}
