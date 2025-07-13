package com.vivek.firstmed.doctor_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.firstmed.doctor_service.dto.DoctorDto;
import com.vivek.firstmed.doctor_service.entity.Doctor;
import com.vivek.firstmed.doctor_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.doctor_service.repository.DoctorRepository;
import com.vivek.firstmed.doctor_service.util.DoctorMapperUtil;
import com.vivek.firstmed.doctor_service.util.IdGeneratorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final IdGeneratorService idGeneratorService;
    private final DoctorMapperUtil doctorMapperUtil;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
            IdGeneratorService idGeneratorService,
            DoctorMapperUtil doctorMapperUtil) {
        this.doctorRepository = doctorRepository;
        this.idGeneratorService = idGeneratorService;
        this.doctorMapperUtil = doctorMapperUtil;
    }

    @Transactional
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        String newId = idGeneratorService.generateDoctorId();
        doctorDto.setDoctorId(newId);
        Doctor doctor = doctorMapperUtil.dtoToEntity(doctorDto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapperUtil.entityToDto(savedDoctor);
    }

    @Transactional(readOnly = true)
    public DoctorDto getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId)
                .map(doctorMapperUtil::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId));
    }

    @Transactional
    public DoctorDto updateDoctor(DoctorDto doctorDto) {
        return doctorRepository.findById(doctorDto.getDoctorId())
                .map(existingDoctor -> {
                    Doctor updatedDoctor = doctorMapperUtil.dtoToEntity(doctorDto);
                    Doctor savedDoctor = doctorRepository.save(updatedDoctor);
                    return doctorMapperUtil.entityToDto(savedDoctor);
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Doctor not found with ID: " + doctorDto.getDoctorId()));
    }

    @Transactional
    public void deleteDoctor(String doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException ("Doctor not found with ID: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> getAllDoctors() {
        List<DoctorDto> doctors = doctorRepository.findAll()
                .stream()
                .map(doctorMapperUtil::entityToDto)
                .toList();
        if (doctors.isEmpty()) {
            throw new ResourceNotFoundException("No doctors found");
        }
        return doctors;
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
        Optional<Doctor> doctorOptional = doctorRepository.findBySpecialization(specialization);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return List.of(doctorMapperUtil.entityToDto(doctor));
        } else {
            throw new ResourceNotFoundException("No doctors found with specialization: " + specialization);
        }
    }

    @Override
    public List<DoctorDto> getDoctorsByLocation(String location) {
        Optional<Doctor> doctorOptional = doctorRepository.findByLocation(location);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return List.of(doctorMapperUtil.entityToDto(doctor));
        } else {
            throw new ResourceNotFoundException("No doctors found in location: " + location);
        }
    }

}
