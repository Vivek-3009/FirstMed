package com.vivek.firstmed.doctor_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
