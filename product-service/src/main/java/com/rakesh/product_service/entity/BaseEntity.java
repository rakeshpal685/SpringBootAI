package com.rakesh.product_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity is a reusable abstract class that provides common audit-related fields for all JPA entities.
 * It includes automatic tracking of creation and modification times, user information, optimistic locking, and primary key management.
 * This class is intended to be extended by all entities in the application that require consistent auditing and ID generation.
 */
@MappedSuperclass // Indicates that this class is not an entity itself but its fields will be inherited by entity subclasses.
@EntityListeners(AuditingEntityListener.class) // Registers the Spring Data auditing listener to automatically populate audit fields.
@Getter // Lombok: Generates getters for all fields at compile time.
@Setter // Lombok: Generates setters for all fields at compile time.
public abstract class BaseEntity {

    /**
     * The version field used for optimistic locking.
     * It is automatically incremented by the persistence provider whenever the entity is updated.
     * Helps prevent concurrent update conflicts.
     */
    @Version // Enables optimistic locking by tracking entity versions.
    private Integer version;

    /**
     * The timestamp of when the entity was created.
     * Automatically populated when the entity is first persisted (inserted).
     * Cannot be updated once set.
     */
    @CreationTimestamp // Hibernate: Automatically sets the timestamp at insert time.
    @Column(name = "created_at", updatable = false) // Maps to 'created_at' column and prevents updates.
    private LocalDateTime createdAt;

    /**
     * The timestamp of the last time the entity was updated.
     * Automatically populated every time the entity is modified.
     */
    @UpdateTimestamp // Hibernate: Automatically updates the timestamp on update.
    @Column(name = "updated_at") // Maps to 'updated_at' column.
    private LocalDateTime updatedAt;

    /**
     * The username or identifier of the user who created the entity.
     * Automatically populated by Spring Data JPA's auditing system.
     */
    @CreatedBy // Spring Data: Automatically sets this field with the current user's name at creation.
    @Column(name = "created_by", updatable = false) // Prevents this column from being modified after creation.
    private String createdBy;

    /**
     * The username or identifier of the user who last modified the entity.
     * Automatically updated by Spring Data JPA on every update operation.
     */
    @LastModifiedBy // Spring Data: Automatically sets this field with the current user's name at update.
    @Column(name = "updated_by") // Maps to 'updated_by' column in the database.
    private String updatedBy;
}
