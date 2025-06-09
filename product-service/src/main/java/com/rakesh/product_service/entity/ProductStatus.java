package com.rakesh.product_service.entity;

/**
 * Represents the possible status values for a product in the system.
 * This enum is mapped to a database column using `@Enumerated(EnumType.STRING)`
 * in the Product entity, ensuring that the string names of these constants
 * (e.g., "AVAILABLE", "OUT_OF_STOCK") are stored in the database.
 */
public enum ProductStatus {
    AVAILABLE, OUT_OF_STOCK, DISCONTINUED
}