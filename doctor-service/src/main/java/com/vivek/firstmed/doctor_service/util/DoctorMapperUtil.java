package com.vivek.firstmed.doctor_service.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vivek.firstmed.doctor_service.dto.DoctorDto;
import com.vivek.firstmed.doctor_service.dto.UpdateDoctorDto;
import com.vivek.firstmed.doctor_service.entity.Doctor;

@Component
public class DoctorMapperUtil {

    private final ModelMapper modelMapper;

    public DoctorMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DoctorDto entityToDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return modelMapper.map(doctor, DoctorDto.class);
    }

    public Doctor dtoToEntity(DoctorDto doctorDto) {
        if (doctorDto == null) {
            return null;
        }
        return modelMapper.map(doctorDto, Doctor.class);
    }

    public Doctor notNullFieldDtoToEntity(UpdateDoctorDto updateDoctorDto, Doctor existingDoctor) {
        if (updateDoctorDto == null) {
            return null;
        }
        modelMapper.map(updateDoctorDto, existingDoctor);
        return existingDoctor;
    }
}