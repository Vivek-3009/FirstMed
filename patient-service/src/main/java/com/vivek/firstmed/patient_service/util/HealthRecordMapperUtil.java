package com.vivek.firstmed.patient_service.util;

import org.modelmapper.ModelMapper;

import com.vivek.firstmed.patient_service.dto.HealthRecordDto;
import com.vivek.firstmed.patient_service.entity.HealthRecord;

public class HealthRecordMapperUtil {

    private final ModelMapper modelMapper;

    public HealthRecordMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HealthRecordDto entityToDto(HealthRecord healthRecord) {
        if (healthRecord == null) {
            return null;
        }
        return modelMapper.map(healthRecord, HealthRecordDto.class);
    }

    public HealthRecord dtoToEntity(HealthRecordDto healthRecordDto) {
        if (healthRecordDto == null) {
            return null;
        }
        return modelMapper.map(healthRecordDto, HealthRecord.class);
    }

}
