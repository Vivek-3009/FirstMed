package com.vivek.firstmed.patient_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Standard API response format")
public class ServiceApiResponse<T> {
    @Schema(example = "success", description = "Status of the response")
    private String status;
    @Schema(example = "Patient created successfully", description = "Descriptive message about the response")
    private String message; 
    @Schema(description = "Payload data")
    private T data;
    
}
