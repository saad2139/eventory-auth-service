package com.eventory.inventory.dto.requests;

public record UpdateItemRequest(
    String name,
    String description,
    String sku,    
    String category,
    String imageUrl
) {
    // All fields optional for partial updates
}
