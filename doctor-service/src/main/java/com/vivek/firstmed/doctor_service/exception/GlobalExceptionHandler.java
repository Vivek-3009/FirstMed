package com.vivek.firstmed.doctor_service.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                Map<String, String> fieldErrors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation Failed",
                                "One or more fields are invalid",
                                request.getRequestURI(),
                                fieldErrors);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
                        HttpServletRequest request) {
                Map<String, String> fieldErrors = new HashMap<>();
                ex.getConstraintViolations().forEach(violation -> {
                        String field = violation.getPropertyPath().toString();
                        fieldErrors.put(field, violation.getMessage());
                });

                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Constraint Violation",
                                "Validation failed for parameters",
                                request.getRequestURI(),
                                fieldErrors);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
                        HttpServletRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                "Resource Not Found",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex,
                        HttpServletRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InvalidFormatException.class)
        public ResponseEntity<ErrorResponse> handleGenericException(InvalidFormatException ex,
                        HttpServletRequest request) {
                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(InvalidFormatException.class)
        public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex,
                        HttpServletRequest request) {

                Map<String, String> fieldErrors = new HashMap<>();

                List<JsonMappingException.Reference> path = ex.getPath();
                String field = path.isEmpty() ? "unknown" : path.get(path.size() - 1).getFieldName();

                String expectedType = ex.getTargetType().getSimpleName();
                Object invalidValue = ex.getValue();

                String message = switch (expectedType) {
                        case "LocalDate" -> String.format(
                                        "Invalid format for field '%s'. Value '%s' is not a valid date. Please use 'yyyy-MM-dd' (e.g., 2025-07-14).",
                                        field, invalidValue);
                        case "LocalTime" -> String.format(
                                        "Invalid format for field '%s'. Value '%s' is not a valid time. Please use 'HH:mm' in 24-hour format (e.g., 14:30).",
                                        field, invalidValue);
                        case "Enum" -> {
                                String validValues = Arrays.toString(ex.getTargetType().getEnumConstants());
                                yield String.format(
                                                "Invalid value '%s' for field '%s'. Please use one of the allowed values: %s.",
                                                invalidValue, field, validValues);
                        }
                        default -> String.format(
                                        "Invalid format for field '%s'. Value '%s' does not match expected type: %s.",
                                        field, invalidValue, expectedType);
                };

                fieldErrors.put(field, message);

                ErrorResponse errorResponse = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Invalid Format",
                                String.format("The entered value '%s' is not valid for field '%s'.", invalidValue, field),
                                request.getRequestURI(),
                                fieldErrors);

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
}