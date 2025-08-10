package com.vivek.firstmed.appointment_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.appointment_service.client.DoctorClient;
import com.vivek.firstmed.appointment_service.client.PatientClient;
import com.vivek.firstmed.appointment_service.dto.AppointmentDto;
import com.vivek.firstmed.appointment_service.dto.AppointmentResponseDto;
import com.vivek.firstmed.appointment_service.dto.DoctorDto;
import com.vivek.firstmed.appointment_service.dto.PatientDto;
import com.vivek.firstmed.appointment_service.dto.RescheduleAppointmentDto;
import com.vivek.firstmed.appointment_service.dto.UpdateAppointmentDto;
import com.vivek.firstmed.appointment_service.entity.Appointment;
import com.vivek.firstmed.appointment_service.enums.AppointmentStatus;
import com.vivek.firstmed.appointment_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.appointment_service.repository.AppointmentRepository;
import com.vivek.firstmed.appointment_service.util.AppointmentMapperUtil;
import com.vivek.firstmed.appointment_service.util.IdGeneratorService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final IdGeneratorService idGeneratorService;
    private final AppointmentMapperUtil appointmentMapperUtil;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
            IdGeneratorService idGeneratorService,
            AppointmentMapperUtil appointmentMapperUtil, PatientClient patientClient,
            DoctorClient doctorClient) {
        this.appointmentRepository = appointmentRepository;
        this.idGeneratorService = idGeneratorService;
        this.appointmentMapperUtil = appointmentMapperUtil;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @Override
    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentDto appointmentDto) {
        String newId = idGeneratorService.generateAppointmentId();
        appointmentDto.setAppointmentId(newId);
        Appointment appointment = appointmentMapperUtil.dtoToEntity(appointmentDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        PatientDto patientDto = patientClient.getPatientById(appointmentDto.getPatientId());
        if (patientDto == null) {
            throw new ResourceNotFoundException("Patient not found with ID: " + appointmentDto.getPatientId());
        }
        DoctorDto doctorDto = doctorClient.getDoctorById(appointmentDto.getDoctorId());
        if (doctorDto == null) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + appointmentDto.getDoctorId());
        }
        return appointmentMapperUtil.entityToResponseDto(savedAppointment, patientDto, doctorDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDto getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
        PatientDto patientDto = patientClient.getPatientById(appointment.getPatientId());
        if (patientDto == null) {
            throw new ResourceNotFoundException("Patient not found with ID: " + appointment.getPatientId());
        }
        DoctorDto doctorDto = doctorClient.getDoctorById(appointment.getDoctorId());
        if (doctorDto == null) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
        }
        return appointmentMapperUtil.entityToResponseDto(appointment, patientDto, doctorDto);        
    }

    @Override
    @Transactional
    public AppointmentResponseDto updateAppointment(UpdateAppointmentDto updateAppointmentDto) {
        return appointmentRepository.findById(updateAppointmentDto.getAppointmentId())
                .map(existingAppointment -> {
                    existingAppointment = appointmentMapperUtil.notNullFieldDtoToEntity(updateAppointmentDto,
                            existingAppointment);
                    PatientDto patientDto = patientClient.getPatientById(existingAppointment.getPatientId());
                    if (patientDto == null) {
                        throw new ResourceNotFoundException("Patient not found with ID: " + existingAppointment.getPatientId());
                    }
                    DoctorDto doctorDto = doctorClient.getDoctorById(existingAppointment.getDoctorId());
                    if (doctorDto == null) {
                        throw new ResourceNotFoundException("Doctor not found with ID: " + existingAppointment.getDoctorId());
                    }
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToResponseDto(updatedAppointment, patientDto, doctorDto);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + updateAppointmentDto.getAppointmentId()));
    }

    @Override
    @Transactional
    public void deleteAppointment(String appointmentId) {
        if (appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Appointment not found with ID: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    @Transactional
    public AppointmentResponseDto confirmAppointment(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setStatus(AppointmentStatus.CONFIRMED);
                    PatientDto patientDto = patientClient.getPatientById(existingAppointment.getPatientId());
                    if( patientDto == null) {
                        throw new ResourceNotFoundException("Patient not found with ID: " + existingAppointment.getPatientId());
                    }
                    DoctorDto doctorDto = doctorClient.getDoctorById(existingAppointment.getDoctorId());
                    if( doctorDto == null) {
                        throw new ResourceNotFoundException("Doctor not found with ID: " + existingAppointment.getDoctorId());
                    }
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToResponseDto(updatedAppointment, patientDto, doctorDto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
    }

    @Override
    @Transactional
    public AppointmentResponseDto cancelAppointment(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setStatus(AppointmentStatus.CANCELLED);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    PatientDto patientDto = patientClient.getPatientById(existingAppointment.getPatientId());
                    if( patientDto == null) {
                        throw new ResourceNotFoundException("Patient not found with ID: " + existingAppointment.getPatientId());
                    }
                    DoctorDto doctorDto = doctorClient.getDoctorById(existingAppointment.getDoctorId());
                    if( doctorDto == null) {
                        throw new ResourceNotFoundException("Doctor not found with ID: " + existingAppointment.getDoctorId());
                    }
                    return appointmentMapperUtil.entityToResponseDto(updatedAppointment, patientDto, doctorDto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> getAllAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found");
        }
        return appointments.map(appointment -> {
            PatientDto patientDto = patientClient.getPatientById(appointment.getPatientId());
            if (patientDto == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + appointment.getPatientId());
            }
            DoctorDto doctorDto = doctorClient.getDoctorById(appointment.getDoctorId());
            if (doctorDto == null) {
                throw new ResourceNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
            }
            return appointmentMapperUtil.entityToResponseDto(appointment, patientDto, doctorDto);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> getAppointmentByPatientId(Pageable pageable, String patientId) {
        Page<Appointment> appointments = appointmentRepository.findByPatientId(patientId, pageable);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for patient with ID: " + patientId);
        }
        return appointments.map(appointment -> {
            PatientDto patientDto = patientClient.getPatientById(appointment.getPatientId());
            if (patientDto == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + appointment.getPatientId());
            }
            DoctorDto doctorDto = doctorClient.getDoctorById(appointment.getDoctorId());
            if (doctorDto == null) {
                throw new ResourceNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
            }
            return appointmentMapperUtil.entityToResponseDto(appointment, patientDto, doctorDto);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> getAppointmentByDoctorId(Pageable pageable, String doctorId) {
        Page<Appointment> appointments = appointmentRepository.findByDocotorId(doctorId, pageable);                
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for doctor with ID: " + doctorId);
        }
        return appointments.map(appointment -> {
            PatientDto patientDto = patientClient.getPatientById(appointment.getPatientId());
            if (patientDto == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + appointment.getPatientId());
            }
            DoctorDto doctorDto = doctorClient.getDoctorById(appointment.getDoctorId());
            if (doctorDto == null) {
                throw new ResourceNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
            }
            return appointmentMapperUtil.entityToResponseDto(appointment, patientDto, doctorDto);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> getAppointmentByDate(Pageable pageable, String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        Page<Appointment> appointments = appointmentRepository.findByAppointmentDate(appointmentDate, pageable);
        if (appointments.isEmpty()) {
            throw new ResourceNotFoundException("No appointments found for date: " + date);
        }
        return appointments.map(appointment -> {
            PatientDto patientDto = patientClient.getPatientById(appointment.getPatientId());
            if (patientDto == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + appointment.getPatientId());
            }
            DoctorDto doctorDto = doctorClient.getDoctorById(appointment.getDoctorId());
            if (doctorDto == null) {
                throw new ResourceNotFoundException("Doctor not found with ID: " + appointment.getDoctorId());
            }
            return appointmentMapperUtil.entityToResponseDto(appointment, patientDto, doctorDto);
        });
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    @Transactional
    public AppointmentDto rescheduleAppointment(RescheduleAppointmentDto rescheduleAppointmentDto) {
        return appointmentRepository.findById(rescheduleAppointmentDto.getAppointmentId())
                .map(existingAppointment -> {
                    existingAppointment.setAppointmentDate(rescheduleAppointmentDto.getNewAppointmentDate());
                    existingAppointment.setStartTime(rescheduleAppointmentDto.getNewStartTime());
                    existingAppointment.setEndTime(rescheduleAppointmentDto.getNewEndTime());
                    existingAppointment.setStatus(AppointmentStatus.RESCHEDULED);
                    Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
                    return appointmentMapperUtil.entityToDto(updatedAppointment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with ID: " + rescheduleAppointmentDto.getAppointmentId()));

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
