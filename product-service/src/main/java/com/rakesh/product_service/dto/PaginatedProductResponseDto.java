package com.rakesh.product_service.dto;

import lombok.Data;

import java.util.List;

@Data
// This DTO is primarily for Swagger documentation clarity.
// Spring's Page object will be serialized directly by default.
public class PaginatedProductResponseDto {
    private List<ProductDto> content;
    private long totalElements;
    private int totalPages;
    private int number; // current page number
    private int size; // page size
    private int numberOfElements; // elements in the current page
    private boolean first;
    private boolean last;
    private boolean empty;
}

//You don't strictly need to create this DTO for the code to work because Spring handles the serialization of the Page object.
// However, it helps document the response structure clearly in Swagger