package com.vivek.firstmed.doctor_service.util;

import org.springframework.stereotype.Component;

import com.vivek.firstmed.doctor_service.repository.DoctorRepository;

@Component
public class IdGeneratorService {
    
    private final DoctorRepository doctorRepository;

    public IdGeneratorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public String generateDoctorId() {
        return doctorRepository.findTopByOrderByDoctorIdDesc()
                .map(doctor -> {
                    String lastId = doctor.getDoctorId();
                    int newId = Integer.parseInt(lastId.substring(1)) + 1;
                    return "D" + newId;
                })
                .orElse("D1000");
    }
}
