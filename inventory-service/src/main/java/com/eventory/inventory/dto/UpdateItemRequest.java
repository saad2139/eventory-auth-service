package com.eventory.inventory.dto;

public record UpdateItemRequest(
    String name,
    String description,
    String sku,    
    String category,
    String imageUrl
) {
    // All fields optional for partial updates
}
