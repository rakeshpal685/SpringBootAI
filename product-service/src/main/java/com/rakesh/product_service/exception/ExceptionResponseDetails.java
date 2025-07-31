package com.rakesh.product_service.exception;


import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponseDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;

    public ExceptionResponseDetails(LocalDateTime timestamp, String message, String path, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }

}