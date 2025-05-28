package com.rakesh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date; // Using java.util.Date for @Temporal

@Entity
@Table(name = "products", // Specifies the table name
        uniqueConstraints = {@UniqueConstraint(columnNames = "sku")}) // Ensures SKU is unique
@Cacheable // Enables caching for this entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // Specifies caching strategy
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Product {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the primary key generation strategy
    @Column(name = "id") // Specifies the column name in the table
    private Long id;

    @Column(name = "name", nullable = false, length = 100) // Column properties
    private String name;

    @Column(name = "description", columnDefinition = "TEXT") // Defines the SQL data type
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2) // Precision and scale for BigDecimal
    private BigDecimal price;

    @Column(name = "sku", nullable = false, unique = true, length = 50) // Stock Keeping Unit
    private String sku;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @CreationTimestamp // Automatically sets the creation timestamp
    @Column(name = "created_at", updatable = false) // Cannot be updated after creation
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically updates the timestamp on modification
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Hibernate Specific Annotations ---
/*    Purpose: Used for optimistic locking. It's a mechanism to prevent lost updates when multiple users or processes try to modify the same record concurrently.
    How it Works: Hibernate (or any JPA provider) uses this field to track the version of the entity. When an entity is loaded, its version is stored. When you try to update the entity, Hibernate checks if the version in the database matches the version that was loaded.

    If the versions match, the update proceeds, and the @Version field is incremented.
    If the versions don't match (meaning another process modified the record since it was loaded), Hibernate throws an OptimisticLockException, indicating a conflict.


    Data Type: Typically an Integer, Long, Short, Timestamp, or Calendar.
    Usage: Add @Version to a field in your entity. Hibernate automatically manages this field; you don't need to set it manually.*/
    @Version
    @Column(name = "version") // Optional: specify the column name
    private Integer version;

    /*Purpose: Specifies the type of a java.util.Date or java.util.Calendar field when mapping it to a database column. While java.time (like LocalDateTime) is generally preferred with modern JPA/Hibernate, @Temporal is needed if you're working with legacy Date or Calendar objects.
    TemporalType Options:

    TemporalType.DATE: Maps to a database date type (stores only the date part, no time).
    TemporalType.TIME: Maps to a database time type (stores only the time part, no date).
    TemporalType.TIMESTAMP: Maps to a database timestamp type (stores both date and time).


    Usage: Place @Temporal on a Date or Calendar field and specify the desired TemporalType.*/
    @Temporal(TemporalType.DATE) // Specifies the mapping for java.util.Date/Calendar
    @Column(name = "release_date")
    private Date releaseDate;

   /* Purpose: Indicates that the field maps to a large object database type (LOB). This is used for storing large amounts of data that don't fit well in standard column types, such as images, videos, or very long text.
    Mapping:

    For character data (String, char[]), it maps to a CLOB (Character Large Object).
    For binary data (byte[], Byte[]), it maps to a BLOB (Binary Large Object).


    Usage: Apply @Lob to the field you want to store as a LOB. Hibernate handles the streaming of large data to and from the database.*/
    @Lob // For large objects (e.g., images, large text)
    @Column(name = "image")
    private byte[] image; // Example: storing an image as byte array

    /*Purpose: Defines how a Java enum should be persisted in the database.
    EnumType Options:

    EnumType.ORDINAL: Persists the enum's ordinal value (its position in the enum declaration, starting from 0) as an integer. Caution: This can be fragile if you reorder or insert new values into your enum.
    EnumType.STRING: Persists the enum's name (the string representation) as a string. Recommended: This is generally safer as it's less affected by changes to the enum's order.


    Usage: Apply @Enumerated to the enum field in your entity and specify the EnumType.*/
    @Enumerated(EnumType.STRING) // Maps the enum to a String in the database
    @Column(name = "status")
    private ProductStatus status; // we need to define the ProductStatus enum

    /*Purpose: Marks a field in an entity class that should not be persisted to the database.
    How it Works: Hibernate will ignore this field during the mapping process. It's useful for fields that are derived from other persistent fields or are only needed for temporary calculations or state within your application logic.
    Usage: Simply add @Transient to the field you don't want to persist.*/
    @Transient // Marks a field that should not be persisted
    private String temporaryField;

   /* Purpose: Allows you to define and use "value objects" or "component objects" within your entities. An embeddable class's fields are mapped directly into the columns of the embedding entity's table. This helps to organize your entity's data and promote code reuse.
    @Embeddable: Applied to the class that you want to embed. Fields within this class can also have JPA mapping annotations (@Column, etc.).
    @Embedded: Applied to the field within the entity that holds an instance of the embeddable class. Hibernate will map the embeddable class's fields to columns in the entity's table. You can use @AttributeOverride on the @Embedded field to customize column names if needed to avoid name collisions.
            Usage: Create a separate class marked with @Embeddable. Then, in your entity, declare a field of that embeddable type and annotate it with @Embedded.*/
    @Embedded // Embeds the fields of the Embeddable class into this entity's table
    private ProductDetails details; // we need to define the ProductDetails Embeddable class (Entity)


}
