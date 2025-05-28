package com.rakesh.product_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Marks this class as embeddable
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class ProductDetails {

    @Column(name = "manufacturer") // Specify column names in the embedding table
    private String manufacturer;

    @Column(name = "weight_grams")
    private Double weightGrams;
}

