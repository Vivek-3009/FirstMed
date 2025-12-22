package com.vivek.firstmed.appointment_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.dto.UpdatePrescriptionDto;
import com.vivek.firstmed.appointment_service.entity.Prescription;
import com.vivek.firstmed.appointment_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.appointment_service.repository.PrescriptionRepository;
import com.vivek.firstmed.appointment_service.util.IdGeneratorService;
import com.vivek.firstmed.appointment_service.util.PrescriptionMapperUtil;

public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapperUtil prescriptionMapperUtil;
    private final IdGeneratorService idGeneratorService;
    
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                    PrescriptionMapperUtil prescriptionMapperUtil,
                                    IdGeneratorService idGeneratorService) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapperUtil = prescriptionMapperUtil;
        this.idGeneratorService = idGeneratorService;
    }

    @Override
    @Transactional
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        String prescriptionId = idGeneratorService.generatePrescriptionId();
        prescriptionDto.setPrescriptionId(prescriptionId);
        Prescription prescription = prescriptionMapperUtil.dtoToEntity(prescriptionDto);
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapperUtil.entityToDto(savedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto getPrescriptionById(String prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescriptionMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + prescriptionId));
    }

    @Override
    @Transactional
    public PrescriptionDto updatePrescription(UpdatePrescriptionDto updatePrescriptionDto) {
        return prescriptionRepository.findById(updatePrescriptionDto.getPrescriptionId())
                .map(existingPrescription -> {
                    existingPrescription = prescriptionMapperUtil.notNullFieldDtoToEntity(updatePrescriptionDto, existingPrescription);
                    Prescription savedPrescription = prescriptionRepository.save(existingPrescription);
                    return prescriptionMapperUtil.entityToDto(savedPrescription);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + updatePrescriptionDto.getPrescriptionId()));
    }

    @Override
    @Transactional
    public void deletePrescription(String prescriptionId) {
        if (!prescriptionRepository.existsById(prescriptionId)) {
            throw new ResourceNotFoundException("Prescription not found with id: " + prescriptionId);
        }
        prescriptionRepository.deleteById(prescriptionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getAllPrescriptions(Pageable pageable) {
        Page<PrescriptionDto> prescriptions= prescriptionRepository.findAll(pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found");
        }
        return prescriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getPrescriptionsByPatientId(Pageable pageable, String patientId) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentPatientId(patientId, pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for patient with id: " + patientId);
        }
        return prescriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getPrescriptionsByDoctorId(Pageable pageable, String doctorId) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentDoctorId(doctorId, pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for doctor with id: " + doctorId);
        }
        return prescriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getPrescriptionsByAppointmentDate(Pageable pageable, LocalDate apointmentDate) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentAppointmentDate(apointmentDate, pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for apointmentDate: " + apointmentDate);
        }
        return prescriptions;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByDoctorAndApointmentDate(Pageable pageable, String doctorId, LocalDate appointmentDate) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentDoctorIdAndAppointmentAppointmentDate(doctorId, appointmentDate, pageable);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for doctor with id: " + doctorId + " and appointment date: " + appointmentDate);
        }
        return prescriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getPrescriptionsByPatientAndApointmentDate(Pageable pageable, String patientId, LocalDate appointmentDate) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentPatientIdAndAppointmentAppointmentDate(patientId, appointmentDate, pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for patient with id: " + patientId + " and appointment date: " + appointmentDate);
        }
        return prescriptions;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDto> getPrescriptionsByAppointmentId(Pageable pageable, String appointmentId) {
        Page<PrescriptionDto> prescriptions = prescriptionRepository.findByApointmentAppointmentId(appointmentId, pageable)
                .map(prescriptionMapperUtil::entityToDto);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("No prescriptions found for appointment with id: " + appointmentId);
        }
        return prescriptions;
    }
    
}
