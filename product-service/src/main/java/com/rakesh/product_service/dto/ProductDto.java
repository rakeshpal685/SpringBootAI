package com.rakesh.product_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rakesh.product_service.entity.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a Product for API communication.
 * This class is used to receive data from clients (e.g., in POST/PUT requests)
 * and to send data to clients (e.g., in GET responses). It separates the API
 * contract from the internal persistence entity (`Product` entity).
 * It also includes Jakarta Bean Validation annotations for input validation.
 */
@Data // Lombok: Automatically generates boilerplate methods like getters, setters,
// toString(), equals(), and hashCode(). This keeps the DTO concise.
@NoArgsConstructor // Lombok: Generates a no-argument constructor. Essential for JSON deserialization
// (Jackson needs to instantiate the object before setting properties).
@AllArgsConstructor // Lombok: Generates a constructor with arguments for all fields. Useful for
// creating instances of the DTO with all values set.
public class ProductDto {

 /*
  * @JsonProperty Purpose: @JsonProperty is a Jackson annotation used to customize the mapping
  *   between Java object fields and JSON field names during serialization (Java object to JSON)
  *   and deserialization (JSON to Java object).
  * Usage: You place @JsonProperty("jsonFieldName") above the field in your DTO.
  *   - Serialization: When a `ProductDto` is converted to JSON, the `id` field will appear as "productId".
  *   - Deserialization: When incoming JSON contains a field "productId", Jackson will map its value to the `id` field of the `ProductDto`.
  * Benefit: Provides flexibility in your API contract. You can use descriptive JSON field names
  *   that might be different from your internal Java field names (e.g., `id` vs. `productId`),
  *   maintaining a consistent API style or integrating with systems that have specific naming conventions.
  */
 @JsonProperty("productId") // Maps the internal 'id' field to 'productId' in JSON.
 private Long id; // Unique identifier for the product. Can be null for new products.

 @NotBlank(message = "Product name is required") // Validation: Ensures the 'name' field is not null, empty, or just whitespace.
 @Size(max = 100, message = "Product name cannot exceed 100 characters") // Validation: Ensures 'name' string length is within limits.
 @JsonProperty("productName") // Maps the internal 'name' field to 'productName' key in JSON, If teh JSON key has same name then no need of this.
 private String name; // The name of the product. When using MapStruct, this name has to be same as the entity variable name, else we have to map it specifically in the ProductMapper class

 @JsonProperty("productDescription") // Maps the internal 'description' field to 'productDescription' in JSON.
 private String description; // A detailed description of the product.

 @NotNull(message = "Price is required") // Validation: Ensures the 'price' field is not null.
 @DecimalMin(value = "0.01", message = "Price must be greater than zero") // Validation: Ensures 'price' is a positive decimal number.
 private BigDecimal price; // The price of the product. Using BigDecimal for precision.

 @NotBlank(message = "SKU is required") // Validation: Ensures the 'sku' field is not null, empty, or whitespace.
 @Size(max = 50, message = "SKU cannot exceed 50 characters") // Validation: Ensures 'sku' string length is within limits.
 private String sku; // Stock Keeping Unit - a unique identifier for the product.

 @JsonProperty("quantityInStock") // Maps the internal 'quantityInStock' field to 'quantityInStock' in JSON.
 private Integer quantityInStock; // The number of units of the product currently in stock.

 @NotNull(message = "Product status is required") // Validation: Ensures the 'status' field is not null.
 private ProductStatus status; // The current status of the product (e.g., AVAILABLE, OUT_OF_STOCK).

 // These fields are typically read-only from the API perspective for creation/update
 // and are populated by the service/database layer upon persistence.
 // They are included in the DTO for GET responses.
 private LocalDateTime createdAt; // Timestamp indicating when the product record was created.

 private LocalDateTime updatedAt; // Timestamp indicating when the product record was last updated.

 /**
  * Represents embedded details of the product.
  * When deserializing JSON, Jackson will map JSON fields corresponding to `ProductDetailsDto`
  * into this object. When serializing, it will embed this object's fields directly
  * into the main product JSON object (unless you customize with `@JsonUnwrapped`).
  * This allows for structured data within the main DTO.
  */
 @JsonProperty("details") // Maps the internal 'details' object to a JSON object named 'details'.
 // You could use @JsonUnwrapped here if you want its fields flattened into the main ProductDto JSON.
/**
 * @Valid: This annotation tells Jakarta Bean Validation to perform validation
 * on the nested 'details' object as well. If 'details' itself is not null,
 * then any validation annotations *within* ProductDetailsDto will be checked.
 * This means that when Spring receives a ProductDto in a request and performs validation on it (because your controller method likely has @Valid on the @RequestBody ProductDto),
 * it will then also apply the validation rules defined inside ProductDetailsDto
 **/
 @Valid // ADD THIS: Ensures validation cascades to ProductDetailsDto
/*(if @Valid is present, but @NotNull is NOT on details in ProductDto): No validation error for details or weightGrams will be shown because details is null,
 and null objects are not validated by @Valid unless they are explicitly marked as @NotNull themselves*/
 @NotNull(message = "Product details are required")
 private ProductDetailsDto details;
}
