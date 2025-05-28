package com.rakesh.product_service.dto;

import com.rakesh.product_service.entity.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Lombok for getters, setters, etc.
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU cannot exceed 50 characters")
    private String sku;

    private Integer quantityInStock;

    private ProductStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // You can add ProductDetailsDto here if you want to expose those details
    // private ProductDetailsDto details;
}
