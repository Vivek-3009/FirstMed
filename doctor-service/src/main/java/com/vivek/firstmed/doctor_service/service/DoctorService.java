package com.vivek.firstmed.doctor_service.service;

import java.util.List;

import com.vivek.firstmed.doctor_service.dto.DoctorDto;

public interface DoctorService {
    DoctorDto addDoctor(DoctorDto doctorDto);
    DoctorDto getDoctorById(String doctorId);
    DoctorDto updateDoctor(DoctorDto doctorDto);
    void deleteDoctor(String doctorId);
    List<DoctorDto> getAllDoctors();
    List<DoctorDto> getDoctorsBySpecialization(String specialization);
    List<DoctorDto> getDoctorsByLocation(String location);
}
