package com.vivek.firstmed.doctor_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.doctor_service.dto.DoctorDto;

public interface DoctorService {
    DoctorDto addDoctor(DoctorDto doctorDto);
    DoctorDto getDoctorById(String doctorId);
    DoctorDto updateDoctor(DoctorDto doctorDto);
    void deleteDoctor(String doctorId);
    Page<DoctorDto> getAllDoctors(Pageable pageable);
    Page<DoctorDto> getDoctorsBySpecialization(Pageable pageable,String specialization);
    Page<DoctorDto> getDoctorsByLocation(Pageable pageable,String location);
}
