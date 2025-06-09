package com.rakesh.product_service.entity;

import jakarta.persistence.Column; // JPA annotation for mapping object fields to database columns
import jakarta.persistence.Embeddable; // JPA annotation marking a class as embeddable
import lombok.AllArgsConstructor; // Lombok for generating a constructor with all fields
import lombok.Data; // Lombok for generating getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // Lombok for generating a no-argument constructor

/**
 * Represents a value object or component object containing additional details about a product.
 * This class is designed to be embedded directly into other entities (like the Product entity),
 * meaning its fields will become columns in the embedding entity's table rather than having its own table.
 */
@Embeddable // Marks this class as an embeddable type. Its fields will be directly mapped into the
// columns of the table belonging to the entity that embeds it (e.g., 'products' table for Product).
@Data // Lombok: A convenient shortcut annotation that bundles @Getter, @Setter, @ToString, @EqualsAndHashCode.
// It automatically generates these methods for all fields in this class.
@NoArgsConstructor // Lombok: Generates a no-argument constructor. This is often required by JPA/Hibernate
// even for embeddable classes to instantiate them during data retrieval.
@AllArgsConstructor // Lombok: Generates a constructor with arguments for all fields declared in this class.
// This provides a convenient way to create new instances of ProductDetails with initial values.
public class ProductDetails {

    @Column(name = "manufacturer") // Maps this field to a database column named 'manufacturer' in the
    // table of the embedding entity (e.g., 'products' table).
    private String manufacturer; // The name of the manufacturer for the product.

    @Column(name = "weight_grams") // Maps this field to a database column named 'weight_grams' in the
    // table of the embedding entity.
    private Double weightGrams; // The weight of the product in grams.

    // You could add more detail fields here, such as:
    // @Column(name = "color")
    // private String color;
    //
    // @Column(name = "material")
    // private String material;
}