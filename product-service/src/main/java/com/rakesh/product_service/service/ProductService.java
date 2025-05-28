package com.rakesh.product_service.service;

import com.rakesh.product_service.dto.ProductDto;
import com.rakesh.product_service.entity.Product;
import com.rakesh.product_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> modelMapper.map(p, ProductDto.class));
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        // Add business logic before saving (e.g., set default status)
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Update entity fields from DTO (ModelMapper can help here too, but manual is clearer sometimes)
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setSku(productDto.getSku());
            product.setQuantityInStock(productDto.getQuantityInStock());
            product.setStatus(productDto.getStatus());
            // createdAt is not updated due to @CreationTimestamp and updatable=false
            // updatedAt is automatically updated by @UpdateTimestamp

            Product updatedProduct = productRepository.save(product);
            return modelMapper.map(updatedProduct, ProductDto.class);
        } else {
            // Consider throwing a custom exception for not found
            return null;
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Add more business methods here, always returning/accepting DTOs
}
