package com.vivek.firstmed.appointment_service.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.AppointmentResponseDto;
import com.vivek.firstmed.appointment_service.dto.DoctorDto;
import com.vivek.firstmed.appointment_service.dto.PatientDto;
import com.vivek.firstmed.appointment_service.dto.UpdateAppointmentDto;
import com.vivek.firstmed.appointment_service.entity.Appointment;

@Component
public class AppointmentMapperUtil {

    private final ModelMapper modelMapper;

    public AppointmentMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentResponseDto entityToResponseDto(Appointment appointment, PatientDto patientDto, DoctorDto doctorDto) {
        if (appointment == null) {
            return null;
        }
        AppointmentResponseDto appointmentResponseDto = modelMapper.map(appointment, AppointmentResponseDto.class);
        appointmentResponseDto.setPatient(patientDto);
        appointmentResponseDto.setDoctor(doctorDto);
        return appointmentResponseDto;
    }

    public Appointment dtoToEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }
        return modelMapper.map(appointmentDto, Appointment.class);
    }

    public Appointment notNullFieldDtoToEntity(UpdateAppointmentDto updateAppointmentDto, Appointment existingAppointment) {
        if (updateAppointmentDto == null) {
            return null;
        }
        modelMapper.map(updateAppointmentDto, existingAppointment);
        return existingAppointment;
    }
}