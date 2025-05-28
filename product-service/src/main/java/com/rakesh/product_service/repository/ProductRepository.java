package com.rakesh.product_service.repository;

import com.rakesh.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA automatically provides basic CRUD operations.
    // You can add custom query methods here, e.g.:
    // List<Product> findByNameContaining(String name);
    // Optional<Product> findBySku(String sku);
}
