package com.rakesh.product_service.service;

import com.rakesh.product_service.dto.ProductMapper;
import com.rakesh.product_service.dto.ProductDto;
import com.rakesh.product_service.entity.Product;
import com.rakesh.product_service.entity.ProductStatus;
import com.rakesh.product_service.exception.ResourceNotFoundException;
import com.rakesh.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // Inject MapStruct mapper

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Retrieve all products from DB and convert them to DTOs.
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll(); // Fetch all products
        return products.stream()
                .map(productMapper::toDto) // Map each entity to DTO using MapStruct
                .toList();
    }

    /**
     * Retrieve a product by its ID. Throws exception if not found.
     */
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product); // Convert entity to DTO
    }

    /**
     * Create a new product by converting DTO to entity and saving it.
     */
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto); // Convert DTO to entity
        Product savedProduct = productRepository.save(product); // Save to DB
        return productMapper.toDto(savedProduct); // Convert back to DTO
    }

    /**
     * Update an existing product by ID with new values from DTO.
     */
    @Transactional // Ensures the method runs within a transaction (commits if successful, rolls back if there's an error)
    public ProductDto updateProduct(Long id, ProductDto productDto) {

        // Fetch the product entity from the database by its ID
        // If not found, throw a ResourceNotFoundException with a message
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update the fields of the existing product entity using values from the provided DTO
        // This uses MapStruct to map fields from the DTO to the entity
        productMapper.updateProductFromDto(productDto, product);

        // Save the updated product entity back to the database
        Product updatedProduct = productRepository.save(product);

        // Convert the updated product entity back into a DTO to return
        return productMapper.toDto(updatedProduct);
    }


    /**
     * Delete a product by its ID after verifying existence.
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id); // Delete the product
    }

    /**
     * Retrieve a paginated and sorted list of products.
     */
    @Transactional(readOnly = true) // Indicates the method is read-only (no data will be modified); improves performance
    public Page<ProductDto> getProductsPaginated(Pageable pageable) {

        // Fetch a page of Product entities from the database using the provided pagination and sorting information
        Page<Product> productPage = productRepository.findAll(pageable);

        // Convert each Product entity in the page to a ProductDto using MapStruct and return the resulting page
        return productPage.map(productMapper::toDto);
    }

    /**
     * Search products by name keyword (case-insensitive).
     */
    @Transactional(readOnly = true)
    public List<ProductDto> searchProductsByName(String nameKeyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(nameKeyword);
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    /**
     * Retrieve products by status string (e.g., "AVAILABLE").
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByStatus(String status) {
        try {
            ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase()); // Convert string to enum
            List<Product> products = productRepository.findByStatus(productStatus); // Query by enum
            return products.stream()
                    .map(productMapper::toDto)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product status: " + status); // Handle invalid input
        }
    }

    /**
     * Retrieve products whose price falls within a given range.
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }
}