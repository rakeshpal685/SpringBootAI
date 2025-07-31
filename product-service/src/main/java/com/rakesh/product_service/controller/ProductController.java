package com.rakesh.product_service.controller;

import com.rakesh.product_service.dto.PaginatedProductResponseDto;
import com.rakesh.product_service.dto.ProductDto;
import com.rakesh.product_service.exception.ExceptionResponseDetails;
import com.rakesh.product_service.exception.ValidationErrorDetails;
import com.rakesh.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "Operations related to products") // Tag for this controller for swagger
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all products.
     * This endpoint fetches all available products from the system.
     *
     * Example URL: `GET /api/products`
     *
     * @return A {@link ResponseEntity} containing a list of {@link ProductDto} objects with HTTP status OK.
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its unique identifier.
     * If no product is found with the given ID, a 404 Not Found response is returned.
     *
     * Example URL: `GET /api/products/123`
     *
     * @param id The unique identifier of the product to retrieve.
     * @return A {@link ResponseEntity} containing the {@link ProductDto} of the found product with HTTP status OK.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDetails.class)))
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a new product with the provided details.
     * The input {@code productDto} is validated before creation.
     *
     * Example URL: `POST /api/products`
     * Example Request Body:
     * ```json
     * {
     *   "name": "New Smartphone",
     *   "description": "Latest model smartphone with advanced features.",
     *   "price": 699.99,
     *   "quantity": 100,
     *   "status": "AVAILABLE"
     * }
     * ```
     *
     * @param productDto The {@link ProductDto} containing the details of the product to create.
     * @return A {@link ResponseEntity} containing the created {@link ProductDto} with HTTP status CREATED.
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details to create", required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ValidationErrorDetails.class)))
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Updates an existing product identified by its ID with the new provided details.
     * The input {@code productDto} is validated. If the product with the given ID is not found,
     * a 404 Not Found response is returned.
     *
     * Example URL: `PUT /api/products/123`
     * Example Request Body:
     * ```json
     * {
     *   "name": "Updated Smartphone",
     *   "description": "Updated description for the latest model.",
     *   "price": 729.99,
     *   "quantity": 95,
     *   "status": "AVAILABLE"
     * }
     * ```
     *
     * @param id The unique identifier of the product to update.
     * @param productDto The {@link ProductDto} containing the updated details for the product.
     * @return A {@link ResponseEntity} containing the updated {@link ProductDto} with HTTP status OK.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product", description = "Update the details of an existing product")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated product details", required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ValidationErrorDetails.class)))
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDetails.class)))
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product identified by its unique identifier.
     * If the product with the given ID is not found, a 404 Not Found response is returned.
     *
     * Example URL: `DELETE /api/products/123`
     *
     * @param id The unique identifier of the product to delete.
     * @return A {@link ResponseEntity} with no content and HTTP status NO_CONTENT if deletion is successful.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDetails.class)))
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a paginated and sorted list of products.
     * This endpoint allows specifying the page number, size, and sorting criteria for the product list.
     *
     * Example URLs:
     * - `GET /api/products/paginated?page=0&size=10` (first page, 10 items)
     * - `GET /api/products/paginated?page=1&size=5&sort=name,asc` (second page, 5 items, sort by name ascending)
     * - `GET /api/products/paginated?sort=price,desc` (default page/size, sort by price descending)
     *
     * @param pageable A {@link Pageable} object containing pagination and sorting information
     *                 (e.g., `?page=0&size=10&sort=name,asc`).
     * @return A {@link ResponseEntity} containing a {@link Page} of {@link ProductDto} objects with HTTP status OK.
     */
    @GetMapping("/paginated")
    @Operation(summary = "Get products with pagination and sorting", description = "Retrieve a paginated and sorted list of products")
    @Parameter(name = "page", description = "Page number (0-indexed)", example = "0")
    @Parameter(name = "size", description = "Number of items per page", example = "10")
    @Parameter(name = "sort", description = "Sorting criteria (e.g., name,price,desc)", example = "name,asc")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated and sorted list of products",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PaginatedProductResponseDto.class)))
    public ResponseEntity<Page<ProductDto>> getProductsPaginated(Pageable pageable) {
        Page<ProductDto> productsPage = productService.getProductsPaginated(pageable);
        return ResponseEntity.ok(productsPage);
    }

    /**
     * Searches for products whose name contains the given keyword.
     * The search is case-insensitive.
     *
     * Example URL: `GET /api/products/search?nameKeyword=phone`
     *
     * @param nameKeyword The keyword to search for within product names.
     * @return A {@link ResponseEntity} containing a list of matching {@link ProductDto} objects with HTTP status OK.
     */
    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Find products whose name contains the given keyword")
    @Parameter(name = "nameKeyword", description = "Keyword to search in product names", required = true)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of matching products",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String nameKeyword) {
        List<ProductDto> products = productService.searchProductsByName(nameKeyword);
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a list of products filtered by their status.
     * The status should be one of the predefined product statuses (e.g., "AVAILABLE", "OUT_OF_STOCK", "DISCONTINUED").
     *
     * Example URLs:
     * - `GET /api/products/status/AVAILABLE`
     * - `GET /api/products/status/OUT_OF_STOCK`
     *
     * @param status The status by which to filter products.
     * @return A {@link ResponseEntity} containing a list of {@link ProductDto} objects matching the given status with HTTP status OK.
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get products by status", description = "Retrieve products filtered by status")
    @Parameter(name = "status", description = "Product status (AVAILABLE, OUT_OF_STOCK, DISCONTINUED)", required = true)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products by status",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    public ResponseEntity<List<ProductDto>> getProductsByStatus(@PathVariable String status) {
        // You might want to validate the status string here or in the service
        List<ProductDto> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a list of products within a specified price range (inclusive).
     *
     * Example URL: `GET /api/products/price-range?minPrice=10.00&maxPrice=100.00`
     *
     * @param minPrice The minimum price for the product search.
     * @param maxPrice The maximum price for the product search.
     * @return A {@link ResponseEntity} containing a list of {@link ProductDto} objects within the specified price range with HTTP status OK.
     */
    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieve products within a specified price range")
    @Parameter(name = "minPrice", description = "Minimum price", required = true, example = "10.00")
    @Parameter(name = "maxPrice", description = "Maximum price", required = true, example = "100.00")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products in price range",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid price range input",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponseDetails.class)))
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        // You might want to validate minPrice < maxPrice here or in the service
        List<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}