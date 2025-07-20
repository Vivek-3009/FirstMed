package com.vivek.firstmed.appointment_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.appointment_service.dto.PrescriptionDto;
import com.vivek.firstmed.appointment_service.entity.Prescription;
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
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + prescriptionId));
    }

    @Override
    @Transactional
    public PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto) {
        return prescriptionRepository.findById(prescriptionDto.getPrescriptionId())
                .map(existingPrescription -> {
                    existingPrescription = prescriptionMapperUtil.notNullFieldDtoToEntity(prescriptionDto, existingPrescription);
                    Prescription savedPrescription = prescriptionRepository.save(existingPrescription);
                    return prescriptionMapperUtil.entityToDto(savedPrescription);
                })
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + prescriptionDto.getPrescriptionId()));
    }

    @Override
    public void deletePrescription(String prescriptionId) {
        // Implementation logic here
    }

    @Override
    public Page<PrescriptionDto> getAllPrescriptions(Pageable pageable) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByPatientId(Pageable pageable, String patientId) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByDoctorId(Pageable pageable, String doctorId) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByDate(Pageable pageable, String date) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByDoctorAndDate(Pageable pageable, String doctorId, String date) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByPatientAndDate(Pageable pageable, String patientId, String date) {
        // Implementation logic here
        return null;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsByAppointmentId(Pageable pageable, String appointmentId) {
        // Implementation logic here
        return null;
    }
    
}
