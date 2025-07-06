package com.vivek.firstmed.appointment_service.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.entity.Appointment;

@Component
public class AppointmentMapperUtil {

    private final ModelMapper modelMapper;

    public AppointmentMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentDto entityToDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    public Appointment dtoToEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }
        return modelMapper.map(appointmentDto, Appointment.class);
    }
}