package com.rakesh.product_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true) // Include fields from the superclass in equals/hashCode
public class ValidationErrorDetails extends ErrorDetails {
    private Map<String, String> validationErrors;

    public ValidationErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode, Map<String, String> validationErrors) {
        super(timestamp, message, path, errorCode);
        this.validationErrors = validationErrors;
    }
}