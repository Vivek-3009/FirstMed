package com.vivek.firstmed.doctor_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.doctor_service.dto.DoctorDto;
import com.vivek.firstmed.doctor_service.dto.UpdateDoctorDto;

public interface DoctorService {
    DoctorDto addDoctor(DoctorDto doctorDto);
    DoctorDto getDoctorById(String doctorId);
    DoctorDto updateDoctor(UpdateDoctorDto updateDoctorDto);
    void deleteDoctor(String doctorId);
    Page<DoctorDto> getAllDoctors(Pageable pageable);
    Page<DoctorDto> getDoctorsBySpecialization(Pageable pageable,String specialization);
    Page<DoctorDto> getDoctorsByLocation(Pageable pageable,String location);
}
