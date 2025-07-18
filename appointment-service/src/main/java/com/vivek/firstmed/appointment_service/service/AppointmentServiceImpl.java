package com.vivek.firstmed.appointment_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
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
                    existingAppointment = appointmentMapperUtil.notNullFieldDtoToEntity(appointmentDto,
                            existingAppointment);
                    Appointment savedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(savedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + appointmentDto.getAppointmentId()));
    }

    @Transactional
    public void deleteAppointment(String appointmentId) {
        if (appointmentRepository.existsById(appointmentId)) {
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

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAllAppointments(Pageable pageable) {
        Page<AppointmentDto> appointments = appointmentRepository.findAll(pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByPatientId(Pageable pageable, String patientId) {
        Page<AppointmentDto> appointments = appointmentRepository.findByPatientId(patientId, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for patient with ID: " + patientId);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByDoctorId(Pageable pageable, String doctorId) {
        Page<AppointmentDto> appointments = appointmentRepository.findByDocotorId(doctorId, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for doctor with ID: " + doctorId);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByDate(Pageable pageable, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        Page<AppointmentDto> appointments = appointmentRepository.findByAppointmentDate(appointmentDate, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for date: " + date);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByStatus(Pageable pageable, String status) {
        AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        Page<AppointmentDto> appointments = appointmentRepository.findByStatus(appointmentStatus, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found with status: " + status);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByDoctorAndDate(Pageable pageable, String doctorId, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        Page<AppointmentDto> appointments = appointmentRepository
                .findByDocotorIdAndAppointmentDate(doctorId, appointmentDate, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No appointments found for doctor with ID: " + doctorId + " on date: " + date);
        }
        return appointments;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDto> getAppointmentByPatientAndDate(Pageable pageable, String patientId, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        Page<AppointmentDto> appointments = appointmentRepository
                .findByPatientIdAndAppointmentDate(patientId, appointmentDate, pageable)
                .map(appointmentMapperUtil::entityToDto);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No appointments found for patient with ID: " + patientId + " on date: " + date);
        }
        return appointments;
    }

    @Transactional
    public AppointmentDto rescheduleAppointment(AppointmentDto appointmentDto) {
        return appointmentRepository.findById(appointmentDto.getAppointmentId())
                .map(existingAppointment -> {
                    appointmentMapperUtil.notNullFieldDtoToEntity(appointmentDto, existingAppointment);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + appointmentDto.getAppointmentId()));

    }

    // @Transactional
    // public AppointmentDto rescheduleAppointment(String appointmentId, String
    // newDate, String newTime) {
    // LocalDate appointmentDate = LocalDate.parse(newDate);
    // LocalTime appointmentTime = LocalTime.parse(newTime);

    // return appointmentRepository.findById(appointmentId)
    // .map(existingAppointment -> {
    // existingAppointment.setAppointmentDate(appointmentDate);
    // existingAppointment.setStartTime(appointmentTime);
    // existingAppointment.setEndTime(appointmentTime.plusHours(1)); // Assuming 1
    // hour appointment duration
    // existingAppointment.setStatus(AppointmentStatus.RESCHEDULED);
    // Appointment updatedAppointment =
    // appointmentRepository.save(existingAppointment);
    // return appointmentMapperUtil.entityToDto(updatedAppointment);
    // })
    // .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with
    // ID: " + appointmentId));
    // }

}
