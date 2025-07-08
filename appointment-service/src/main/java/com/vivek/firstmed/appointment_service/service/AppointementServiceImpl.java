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

public class AppointementServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final IdGeneratorService idGeneratorService;
    private final AppointmentMapperUtil appointmentMapperUtil;

    public AppointementServiceImpl(AppointmentRepository appointmentRepository,
            IdGeneratorService idGeneratorService,
            AppointmentMapperUtil appointmentMapperUtil) {
        this.appointmentRepository = appointmentRepository;
        this.idGeneratorService = idGeneratorService;
        this.appointmentMapperUtil = appointmentMapperUtil;
    }

    @Transactional
    public AppointmentDto addAppointment(AppointmentDto appointmentDto) {
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

    @Override
    public List<AppointmentDto> getAppointmentByPatientId(String patientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByPatientId'");
    }

    @Override
    public List<AppointmentDto> getAppointmentByDoctorId(String doctorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByDoctorId'");
    }

    @Override
    public List<AppointmentDto> getAppointmentByDate(String date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByDate'");
    }

    @Override
    public List<AppointmentDto> getAppointmentByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByStatus'");
    }

    @Override
    public List<AppointmentDto> getAppointmentByDoctorAndDate(String doctorId, String date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByDoctorAndDate'");
    }

    @Override
    public List<AppointmentDto> getAppointmentByPatientAndDate(String patientId, String date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByPatientAndDate'");
    }

    @Override
    public AppointmentDto rescheduleAppointment(String appointmentId, String newDate, String newTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rescheduleAppointment'");
    }

}
