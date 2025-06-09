package com.rakesh.product_service.dto;

import com.rakesh.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { ProductDetailsMapper.class }) // Marks this interface as a MapStruct mapper; 'componentModel = "spring"' makes it a Spring bean; 'uses' specifies other mappers used by this one.
public interface ProductMapper { // Declares the ProductMapper interface, which defines the mapping methods.
   // @Mapping(source = "id", target = "productId"), use this if the dto has field name productId corresponding to field name id in entity
    ProductDto toDto(Product product); // Declares a method to convert a Product entity to a ProductDto (data transfer object).

    //@Mapping(source = "productId", target = "id"), use this if the dto has field name productId corresponding to field name id in entity
    Product toEntity(ProductDto productDto); // Declares a method to convert a ProductDto back to a Product entity.

    //@Mapping(source = "productId", target = "id"), use this if the dto has field name productId corresponding to field name id in entity
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity); // Declares a method to update an existing Product entity using data from a ProductDto.
}

