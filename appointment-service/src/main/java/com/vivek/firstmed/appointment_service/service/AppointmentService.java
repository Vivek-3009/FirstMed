package com.vivek.firstmed.appointment_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.RescheduleAppointmentDto;
import com.vivek.firstmed.appointment_service.dto.UpdateAppointmentDto;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto);
    AppointmentDto getAppointmentById(String appointmentId);
    AppointmentDto updateAppointment(UpdateAppointmentDto updateAppointmentDto);
    void deleteAppointment(String appointmentId);
    Page<AppointmentDto> getAllAppointments(Pageable pageable);
    Page<AppointmentDto> getAppointmentByPatientId(Pageable pageable, String patientId);
    Page<AppointmentDto> getAppointmentByDoctorId(Pageable pageable, String doctorId);
    Page<AppointmentDto> getAppointmentByDate(Pageable pageable, String date);
    Page<AppointmentDto> getAppointmentByStatus(Pageable pageable, String status);
    Page<AppointmentDto> getAppointmentByDoctorAndDate(Pageable pageable, String doctorId, String date);
    Page<AppointmentDto> getAppointmentByPatientAndDate(Pageable pageable, String patientId, String date);
    AppointmentDto confirmAppointment(String appointmentId);
    AppointmentDto cancelAppointment(String appointmentId);
    AppointmentDto rescheduleAppointment(RescheduleAppointmentDto rescheduleAppointmentDto);
}
