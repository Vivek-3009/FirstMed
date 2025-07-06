package com.vivek.firstmed.appointment_service.util;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.entity.Prescription;

@Component
public class PrescriptionMapperUtil {

    private final ModelMapper modelMapper;

    public PrescriptionMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PrescriptionDto entityToDto(Prescription prescription) {
        if (prescription == null) {
            return null;
        }
        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    public Prescription dtoToEntity(PrescriptionDto prescriptionDto) {
        if (prescriptionDto == null) {
            return null;
        }
        return modelMapper.map(prescriptionDto, Prescription.class);
    }
}