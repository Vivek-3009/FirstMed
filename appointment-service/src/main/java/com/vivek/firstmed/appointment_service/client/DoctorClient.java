package com.vivek.firstmed.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vivek.firstmed.appointment_service.dto.DoctorDto;

@FeignClient(name = "doctor-service", url = "${doctor.service.url}")
public interface DoctorClient {
    @GetMapping("/internal/doctor/{doctorId}")
    DoctorDto getDoctorById(@PathVariable String doctorId);
}
