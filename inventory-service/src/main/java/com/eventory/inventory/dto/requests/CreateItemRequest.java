package com.eventory.inventory.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateItemRequest(
    @NotBlank(message = "Name is required")
    String name,
    String description,
    String sku,
    String category,
    String imageUrl
) {}
