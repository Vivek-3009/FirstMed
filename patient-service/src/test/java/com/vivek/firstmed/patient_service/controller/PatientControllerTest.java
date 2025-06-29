package com.vivek.firstmed.patient_service.controller;

import com.vivek.firstmed.patient_service.dto.PatientDto;
import com.vivek.firstmed.patient_service.dto.ServiceApiResponse;
import com.vivek.firstmed.patient_service.exception.ResourceNotFoundException;
import com.vivek.firstmed.patient_service.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class PatientControllerTest {

    private PatientService patientService;
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        patientService = mock(PatientService.class);
        patientController = new PatientController(patientService);
    }

    @Test
    void updatePatient_shouldReturnOk_whenPatientExists() {
        String patientId = "123";
        PatientDto inputDto = new PatientDto();
        PatientDto updatedDto = new PatientDto();
        updatedDto.setPatientId(patientId);

        when(patientService.updatePatient(eq(patientId), any(PatientDto.class))).thenReturn(updatedDto);

        ResponseEntity<ServiceApiResponse<PatientDto>> response = patientController.updatePatient(patientId, inputDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Patient updated successfully", response.getBody().getMessage());
        assertEquals(updatedDto, response.getBody().getData());
        verify(patientService, times(1)).updatePatient(eq(patientId), any(PatientDto.class));
    }

    @Test
    void updatePatient_shouldThrowResourceNotFoundException_whenPatientDoesNotExist() {
        String patientId = "notfound";
        PatientDto inputDto = new PatientDto();

        when(patientService.updatePatient(eq(patientId), any(PatientDto.class))).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> patientController.updatePatient(patientId, inputDto)
        );

        assertEquals("Patient not found with id: " + patientId, thrown.getMessage());
        verify(patientService, times(1)).updatePatient(eq(patientId), any(PatientDto.class));
    }

    @Test
    void updatePatient_shouldThrowException_whenPatientIdIsInvalid() {
        String invalidPatientId = ""; // Assuming empty string is invalid
        PatientDto inputDto = new PatientDto();

        // Since validatePatientId is static, it will throw an exception if invalid.
        // We expect an IllegalArgumentException or similar, depending on implementation.
        assertThrows(
                RuntimeException.class,
                () -> patientController.updatePatient(invalidPatientId, inputDto)
        );
        verify(patientService, never()).updatePatient(anyString(), any(PatientDto.class));
    }
}