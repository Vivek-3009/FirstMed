package com.vivek.firstmed.appointment_service.service;

import java.util.List;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;

public interface AppointmentService {
    AppointmentDto addAppointment(AppointmentDto appointmentDto);
    AppointmentDto getAppointmentById(String appointmentId);
    AppointmentDto updateAppointment(AppointmentDto appointmentDto);
    void deleteAppointment(String appointmentId);
    List<AppointmentDto> getAllAppointments();
    List<AppointmentDto> getAppointmentByPatientId(String patientId);
    List<AppointmentDto> getAppointmentByDoctorId(String doctorId);
    List<AppointmentDto> getAppointmentByDate(String date);
    List<AppointmentDto> getAppointmentByStatus(String status);
    List<AppointmentDto> getAppointmentByDoctorAndDate(String doctorId, String date);
    List<AppointmentDto> getAppointmentByPatientAndDate(String patientId, String date);
    AppointmentDto confirmAppointment(String appointmentId);
    AppointmentDto cancelAppointment(String appointmentId);
    AppointmentDto rescheduleAppointment(String appointmentId, String newDate, String newTime);
}
