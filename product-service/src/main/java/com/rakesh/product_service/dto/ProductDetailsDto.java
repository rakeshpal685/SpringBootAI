package com.rakesh.product_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for conveying detailed product information.
 * This DTO mirrors the structure of the ProductDetails entity/embeddable,
 * but specifically for API exposure.
 */
@Data // Lombok: Generates boilerplate code (getters, setters, toString, equals, hashCode).
@NoArgsConstructor // Lombok: Generates a no-argument constructor.
@AllArgsConstructor // Lombok: Generates a constructor with arguments for all fields.
public class ProductDetailsDto {

    @JsonProperty("manufacturerName") // Customizes the JSON field name for 'manufacturer'.
    private String manufacturer; // The name of the product's manufacturer.

    @JsonProperty("itemWeightGrams") // Customizes the JSON field name for 'weightGrams'.
    @NotNull(message = "Weight in grams is necessary") // Use @NotNull for Double field
    @DecimalMin(value = "0.01", message = "Weight in grams must be greater than zero")// Optional: to ensure positive weight
    // The weight of the product in grams.
    private Double weightGrams;
}