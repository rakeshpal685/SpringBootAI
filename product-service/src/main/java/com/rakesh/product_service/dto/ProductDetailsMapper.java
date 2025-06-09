package com.rakesh.product_service.dto;

import com.rakesh.product_service.entity.ProductDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Marks this interface as a MapStruct mapper; 'componentModel = "spring"' makes it available as a Spring bean.
public interface ProductDetailsMapper { // Declares the ProductDetailsMapper interface, which defines the mapping methods for ProductDetails.
    ProductDetailsDto toDto(ProductDetails details); // Declares a method to convert a ProductDetails entity to a ProductDetailsDto.
    ProductDetails toEntity(ProductDetailsDto dto); // Declares a method to convert a ProductDetailsDto back to a ProductDetails entity.
}