package com.vivek.firstmed.patient_service.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vivek.firstmed.patient_service.dto.PatientDto;
import com.vivek.firstmed.patient_service.entity.Patient;

@Component
public class PatientMapperUtil {

    private final ModelMapper modelMapper;

    public PatientMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PatientDto entityToDto(Patient patient) {
        if (patient == null) {
            return null;
        }
        return modelMapper.map(patient, PatientDto.class);
    }

    public Patient dtoToEntity(PatientDto patientDto) {
        if (patientDto == null) {
            return null;
        }
        return modelMapper.map(patientDto, Patient.class);
    }
}