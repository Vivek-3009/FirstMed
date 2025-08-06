package com.vivek.firstmed.appointment_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.AppointmentResponseDto;
import com.vivek.firstmed.appointment_service.dto.RescheduleAppointmentDto;
import com.vivek.firstmed.appointment_service.dto.UpdateAppointmentDto;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentDto appointmentDto);
    AppointmentResponseDto getAppointmentById(String appointmentId);
    AppointmentResponseDto updateAppointment(UpdateAppointmentDto updateAppointmentDto);
    void deleteAppointment(String appointmentId);
    Page<AppointmentResponseDto> getAllAppointments(Pageable pageable);
    Page<AppointmentResponseDto> getAppointmentByPatientId(Pageable pageable, String patientId);
    Page<AppointmentResponseDto> getAppointmentByDoctorId(Pageable pageable, String doctorId);
    Page<AppointmentResponseDto> getAppointmentByDate(Pageable pageable, String date);
    Page<AppointmentResponseDto> getAppointmentByStatus(Pageable pageable, String status);
    Page<AppointmentResponseDto> getAppointmentByDoctorAndDate(Pageable pageable, String doctorId, String date);
    Page<AppointmentResponseDto> getAppointmentByPatientAndDate(Pageable pageable, String patientId, String date);
    AppointmentResponseDto confirmAppointment(String appointmentId);
    AppointmentResponseDto cancelAppointment(String appointmentId);
    AppointmentResponseDto rescheduleAppointment(RescheduleAppointmentDto rescheduleAppointmentDto);
}
