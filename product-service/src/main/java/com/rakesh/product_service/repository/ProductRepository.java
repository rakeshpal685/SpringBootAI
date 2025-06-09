package com.rakesh.product_service.repository;

import com.rakesh.product_service.entity.Product;
import com.rakesh.product_service.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA automatically provides basic CRUD and pagination/sorting.

    // Custom query method: Find products whose name contains the keyword (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String nameKeyword);

    // Custom query method: Find products by their status
    List<Product> findByStatus(ProductStatus status);

    // Custom query method: Find products within a price range
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Spring Data JPA also supports deriving queries from property names
    // For example, you could add:
    // List<Product> findByQuantityInStockLessThan(Integer quantity);

    // You can also use @Query for more complex queries
    // @Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    // List<Product> findProductsGreaterThanPrice(@Param("minPrice") BigDecimal minPrice);
}

