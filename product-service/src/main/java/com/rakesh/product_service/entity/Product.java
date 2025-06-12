package com.rakesh.product_service.entity;

import jakarta.persistence.*; // Core JPA annotations
import lombok.AllArgsConstructor; // Lombok for generating constructor with all fields
import lombok.Data; // Lombok for generating getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // Lombok for generating no-argument constructor
import org.hibernate.annotations.Cache; // Hibernate specific caching annotation
import org.hibernate.annotations.CacheConcurrencyStrategy; // Hibernate specific caching strategy
import org.hibernate.annotations.CreationTimestamp; // Hibernate specific for auto-setting creation timestamp
import org.hibernate.annotations.UpdateTimestamp; // Hibernate specific for auto-setting update timestamp

import java.math.BigDecimal; // For precise decimal numbers (e.g., currency)
import java.time.LocalDateTime; // Modern Java 8 date/time API for timestamps
import java.util.Date; // Legacy Date class, used here specifically for @Temporal example

/**
 * Represents a Product entity mapped to a database table.
 * This class demonstrates various JPA and Hibernate annotations for object-relational mapping.
 */
@Entity // Marks this class as a JPA entity, meaning it maps to a database table.
@Table(name = "products", // Specifies the actual table name in the database. Recommended to be explicit.
        // Defines unique constraints on one or more columns to ensure data integrity.
        uniqueConstraints = {@UniqueConstraint(columnNames = "sku")}) // Ensures that the 'sku' column has unique values across all rows.
@Cacheable // Marks this entity as eligible for the JPA/Hibernate second-level cache.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // Configures the caching strategy. READ_WRITE allows updates, ensuring cache consistency.
@Data // Lombok: Generates boilerplate code like getters, setters, toString(), equals(), and hashCode() methods automatically.
@NoArgsConstructor // Lombok: Generates a no-argument constructor. Required by JPA for entity instantiation.
@AllArgsConstructor // Lombok: Generates a constructor with arguments for all fields. Useful for creating new entity instances.
public class Product {

    @Id // Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures how the primary key values are generated. IDENTITY means the database auto-increments the ID.
    @Column(name = "id") // Specifies the column name in the 'products' table for this field.
    private Long id; // The unique identifier for the product.

    @Column(name = "name", // Maps this field to a database column named 'name'.
            nullable = false, // Specifies that this column cannot contain NULL values.
            length = 100) // Sets the maximum length for the string data in the database column.
    private String name; // The name of the product.

    @Column(name = "description", // Maps this field to a database column named 'description'.
            columnDefinition = "TEXT") // Defines the SQL data type for the column, in this case, a TEXT type for long strings.
    private String description; // A detailed description of the product.

    @Column(name = "price", // Maps this field to a database column named 'price'.
            nullable = false, // Specifies that this column cannot contain NULL values.
            precision = 10, // Total number of digits allowed in the decimal number (e.g., 99,999,999.99).
            scale = 2) // Number of digits after the decimal point (e.g., .99).
    private BigDecimal price; // The price of the product. Using BigDecimal for accuracy.

    @Column(name = "sku", // Maps this field to a database column named 'sku'.
            nullable = false, // Cannot be NULL.
            unique = true, // Ensures that each SKU value is unique across all product records.
            length = 50) // Maximum length for the SKU string.
    private String sku; // Stock Keeping Unit - a unique identifier for each product.

    @Column(name = "quantity_in_stock") // Maps to a database column named 'quantity_in_stock'.
    private Integer quantityInStock; // The number of units currently available in stock.

    @CreationTimestamp // Hibernate specific: Automatically sets the field's value to the current timestamp when the entity is first persisted (inserted).
    @Column(name = "created_at", // Maps to a database column named 'created_at'.
            updatable = false) // Specifies that this column's value cannot be updated after its initial creation.
    private LocalDateTime createdAt; // Timestamp indicating when the product record was created.

    @UpdateTimestamp // Hibernate specific: Automatically updates the field's value to the current timestamp whenever the entity is modified (updated).
    @Column(name = "updated_at") // Maps to a database column named 'updated_at'.
    private LocalDateTime updatedAt; // Timestamp indicating when the product record was last updated.

    // --- Hibernate Specific Annotations (Detailed Explanations) ---

    /**
     * `@Version`
     * Purpose: Used for optimistic locking. It's a mechanism to prevent lost updates when multiple users or processes
     *          try to modify the same database record concurrently without explicit locking.
     * How it Works: Hibernate (or any JPA provider) uses this field to track the version of the entity.
     *          1. When an entity is loaded, its current `version` value is read and stored in memory.
     *          2. When an update operation is performed on the entity, Hibernate checks if the `version` value in the
     *             database matches the `version` value that was loaded.
     *          3. If the versions match, the update proceeds, and the `@Version` field in the database is automatically
     *             incremented by Hibernate.
     *          4. If the versions do *not* match (meaning another transaction modified the record since it was loaded),
     *             Hibernate throws an `OptimisticLockException`, indicating a concurrent modification conflict.
     * Data Type: Typically an `Integer`, `Long`, `Short`, `Timestamp`, or `Calendar`. Hibernate manages this field.
     * Usage: Add `@Version` to a numeric or timestamp field in your entity. You do *not* set or update this field manually in your application code.
     */
    @Version
    @Column(name = "version") // Optional: specifies the column name in the database for the version field.
    private Integer version; // Integer field to store the entity's version for optimistic locking.

    /**
     * `@Temporal(TemporalType.DATE)`
     * Purpose: Specifies the exact database column type for `java.util.Date` or `java.util.Calendar` fields.
     *          This is necessary because SQL databases have different types for dates, times, and timestamps.
     *          While `java.time` (e.g., `LocalDateTime`, `LocalDate`) is generally preferred with modern JPA/Hibernate
     *          (as it maps directly without `@Temporal`), `@Temporal` is needed if you are working with legacy `java.util.Date` or `java.util.Calendar` objects.
     * `TemporalType` Options:
     *   - `TemporalType.DATE`: Maps to a database date type (e.g., `DATE` in MySQL), storing only the date part (year, month, day).
     *   - `TemporalType.TIME`: Maps to a database time type (e.g., `TIME` in MySQL), storing only the time part (hour, minute, second).
     *   - `TemporalType.TIMESTAMP`: Maps to a database timestamp type (e.g., `DATETIME` or `TIMESTAMP` in MySQL), storing both date and time.
     * Usage: Place `@Temporal` on a `Date` or `Calendar` field and specify the desired `TemporalType`.
     */
    @Temporal(TemporalType.DATE) // Configures the `releaseDate` field to map to a database DATE type (only date, no time).
    @Column(name = "release_date") // Maps this field to a database column named 'release_date'.
    private Date releaseDate; // The date when the product was released.

    /**
     * `@Lob`
     * Purpose: Indicates that the field maps to a Large Object (LOB) database type. This is used for storing large amounts of data
     *          that don't fit efficiently in standard SQL column types (e.g., `VARCHAR`, `INT`). Examples include images, videos, large text blocks, or documents.
     * Mapping:
     *   - For character data (e.g., `String`, `char[]`), it typically maps to a `CLOB` (Character Large Object) in the database.
     *   - For binary data (e.g., `byte[]`, `Byte[]`), it typically maps to a `BLOB` (Binary Large Object) in the database.
     * How it Works: Hibernate handles the streaming of large data to and from the database efficiently.
     * Usage: Apply `@Lob` to the field you want to store as a LOB.
     */
    @Lob // Marks this field to be stored as a Large Object (BLOB in this case, as it's a byte array).
    @Column(name = "image") // Maps this field to a database column named 'image'.
    private byte[] image; // Example: Stores product image data as a byte array.

    /**
     * `@Enumerated(EnumType.STRING)`
     * Purpose: Defines how a Java `enum` should be persisted in the database. Enums in Java are distinct types, but databases typically don't have a direct enum type.
     * `EnumType` Options:
     *   - `EnumType.ORDINAL`: (Default, but **discouraged** for production) Persists the enum's ordinal value (its position in the enum declaration, starting from 0) as an integer.
     *     **Caution:** This is fragile! If you reorder, insert, or remove enum constants, existing database data will become inconsistent with the new enum ordinals.
     *   - `EnumType.STRING`: (Recommended) Persists the enum's name (the string representation, e.g., "AVAILABLE") as a `VARCHAR` string in the database.
     *     **Benefit:** This is much safer and more robust as it's less affected by changes to the enum's order or addition of new constants (as long as existing names aren't changed).
     * Usage: Apply `@Enumerated` to the enum field in your entity and explicitly specify `EnumType.STRING` for safety.
     */
    @Enumerated(EnumType.STRING) // Configures the `status` enum to be stored as its string name in the database.
    @Column(name = "status") // Maps this field to a database column named 'status'.
    private ProductStatus status; // The current status of the product (e.g., AVAILABLE, OUT_OF_STOCK).

    /**
     * `@Transient`
     * Purpose: Marks a field in an entity class that should *not* be persisted to the database.
     * How it Works: Hibernate will completely ignore this field during the object-relational mapping process. It will not create a column for it,
     *          nor will it attempt to read or write its value to the database.
     * Use Cases:
     *   - Fields that are derived from other persistent fields (e.g., a `fullName` derived from `firstName` and `lastName`).
     *   - Temporary state or calculated values that are only relevant during runtime within the application logic.
     *   - Fields that are part of a larger object but not meant for database storage.
     * Usage: Simply add `@Transient` to the field you don't want to persist.
     */
    @Transient // Marks this field as non-persistent; it will not be mapped to any database column.
    private String temporaryField; // A field for temporary data not stored in the database.

    /**
     * `@Embeddable` and `@Embedded`
     * Purpose: Allows you to define and use "value objects" or "component objects" within your entities.
     *          This is a way to promote code reuse and organize your entity's data by encapsulating a group of attributes
     *          that logically belong together but don't warrant their own separate entity table.
     *          The fields of an embeddable class are mapped directly into the columns of the embedding entity's table.
     * `@Embeddable`:
     *   - Annotation applied to the class that you want to embed.
     *   - This class typically represents a reusable component (e.g., Address, ContactInfo, ProductDetails).
     *   - Fields within this class can also have JPA mapping annotations (`@Column`, `@NotNull`, etc.), which define how they map to columns in the *embedding* table.
     * `@Embedded`:
     *   - Annotation applied to the field within the entity that holds an instance of the embeddable class.
     *   - When Hibernate encounters this, it takes all the fields from the `@Embeddable` class and maps them as columns directly into the table of the entity containing the `@Embedded` field.
     *   - You can use `@AttributeOverride` on the `@Embedded` field to customize column names if needed to avoid name collisions (e.g., if you embed two `Address` objects, you'd want `shipping_street` and `billing_street`).
     * Usage:
     *   1. Create a separate class marked with `@Embeddable`.
     *   2. In your entity, declare a field of that `@Embeddable` type and annotate it with `@Embedded`.
     */
    @Embedded // Embeds the fields of the `ProductDetails` class directly into the 'products' table.
     /* @AttributeOverrides({
        * @AttributeOverride(name = "street", column = @Column(name = "home_street")),
        *@AttributeOverride(name = "city", column = @Column(name = "home_city")),
        *@AttributeOverride(name = "zipCode", column = @Column(name = "home_zip"))
    *})
    * Use the above If we are using the embeddable class in multiple tables but with different names of the column in the every main table, in this case we have to remove the @column from the embedable class
    *If we wanted to embed ProductDetails twice (e.g., as details and importedDetails) in the same entity, you must override the column names like this:
    *@Embedded
    *@AttributeOverrides({
    *@AttributeOverride(name = "manufacturer", column = @Column(name = "imported_manufacturer"))
    *})
    *private ProductDetails importedDetails;
    */
    private ProductDetails details; // A value object holding additional product details like manufacturer, weight, etc.

    // --- Relationships (Not implemented in this specific entity, but commonly used) ---
    // @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
    // These annotations are used to define associations between different entities (tables).
    // They are crucial for representing the relationships in your domain model (e.g., an Order has many OrderLineItems).
}
