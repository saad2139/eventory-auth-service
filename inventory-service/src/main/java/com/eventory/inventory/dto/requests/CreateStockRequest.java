package com.eventory.inventory.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateStockRequest(
    @NotNull(message = "Item ID is required")
    UUID itemId,
    
    @NotNull(message = "Location ID is required")
    UUID locationId,
    
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    Integer quantity
) {}
