package com.vivek.firstmed.appointment_service.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.RescheduleAppointmentDto;
import com.vivek.firstmed.appointment_service.entity.Appointment;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;
import com.vivek.firstmed.appointment_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.appointment_service.repository.AppointmentRepository;
import com.vivek.firstmed.appointment_service.util.AppointmentMapperUtil;
import com.vivek.firstmed.appointment_service.util.IdGeneratorService;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final IdGeneratorService idGeneratorService;
    private final AppointmentMapperUtil appointmentMapperUtil;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
            IdGeneratorService idGeneratorService,
            AppointmentMapperUtil appointmentMapperUtil) {
        this.appointmentRepository = appointmentRepository;
        this.idGeneratorService = idGeneratorService;
        this.appointmentMapperUtil = appointmentMapperUtil;
    }

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        String newId = idGeneratorService.generateAppointmentId();
        appointmentDto.setAppointmentId(newId);
        Appointment appointment = appointmentMapperUtil.dtoToEntity(appointmentDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapperUtil.entityToDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentDto getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointmentMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
    }

    @Transactional
    public AppointmentDto updateAppointment(AppointmentDto appointmentDto) {
        return appointmentRepository.findById(appointmentDto.getAppointmentId())
                .map(existingAppointment -> {
                    Appointment updatedAppointment = appointmentMapperUtil.dtoToEntity(appointmentDto);
                    updatedAppointment.setAppointmentId(existingAppointment.getAppointmentId());
                    Appointment savedAppointment = appointmentRepository.save(updatedAppointment);
                    return appointmentMapperUtil.entityToDto(savedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + appointmentDto.getAppointmentId()));
    }

    @Transactional
    public void deleteAppointment(String appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Appointment not found with ID: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }

    @Transactional
    public AppointmentDto confirmAppointment(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setStatus(AppointmentStatus.CONFIRMED);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
    }

    @Transactional
    public AppointmentDto cancelAppointment(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setStatus(AppointmentStatus.CANCELLED);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));

    }

    @Transactional
    public AppointmentDto rescheduleAppointment(RescheduleAppointmentDto rescheduleAppointmentDto) {
        return appointmentRepository.findById(rescheduleAppointmentDto.getAppointmentId())
                .map(existingAppointment -> {
                    existingAppointment.setStatus(AppointmentStatus.RESCHEDULED);
                    existingAppointment.setAppointmentDate(rescheduleAppointmentDto.getNewAppointmentDate());
                    existingAppointment.setStartTime(rescheduleAppointmentDto.getNewStartTime());
                    existingAppointment.setEndTime(rescheduleAppointmentDto.getNewEndTime());
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + rescheduleAppointmentDto.getAppointmentId()));

    }
    @Transactional(readOnly = true)
    public List<AppointmentDto> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentRepository.findAll().stream()
                .map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByPatientId(String patientId) {
        List<AppointmentDto> appointments = appointmentRepository.findByPatientId(patientId).stream()
                .map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for patient with ID: " + patientId);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByDoctorId(String doctorId) {
        List<AppointmentDto> appointments = appointmentRepository.findByDocotorId(doctorId).stream().
                map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for doctor with ID: " + doctorId);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByDate(String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        List<AppointmentDto> appointments = appointmentRepository.findByAppointmentDate(appointmentDate).stream()
                                                .map(appointmentMapperUtil::entityToDto).toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for date: " + date);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByStatus(String status) {
        AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        List<AppointmentDto> appointments = appointmentRepository.findByStatus(appointmentStatus).stream()
                .map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found with status: " + status);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByDoctorAndDate(String doctorId, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        List<AppointmentDto> appointments = appointmentRepository.findByDocotorIdAndAppointmentDate(doctorId, appointmentDate).stream()
                .map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for doctor with ID: " + doctorId + " on date: " + date);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentByPatientAndDate(String patientId, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        List<AppointmentDto> appointments = appointmentRepository.findByPatientIdAndAppointmentDate(patientId, appointmentDate).stream()
                .map(appointmentMapperUtil::entityToDto)
                .toList();
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for patient with ID: " + patientId + " on date: " + date);
        }
        return appointments;
    }

    @Transactional
    public AppointmentDto rescheduleAppointment(String appointmentId, String newDate, String newTime) {
        LocalDate appointmentDate = LocalDate.parse(newDate);
        LocalTime appointmentTime = LocalTime.parse(newTime);

        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setAppointmentDate(appointmentDate);
                    existingAppointment.setStartTime(appointmentTime);
                    existingAppointment.setEndTime(appointmentTime.plusHours(1)); // Assuming 1 hour appointment duration
                    existingAppointment.setStatus(AppointmentStatus.RESCHEDULED);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
    }

}
