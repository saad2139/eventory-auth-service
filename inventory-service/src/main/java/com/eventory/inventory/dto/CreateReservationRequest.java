package com.eventory.inventory.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
    @NotNull(message = "Item ID is required")
    UUID itemId,
    
    @NotNull(message = "Location ID is required")
    UUID locationId,
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity,
    
    @NotNull(message = "Start time is required")
    OffsetDateTime startTime,
    
    @NotNull(message = "End time is required")
    OffsetDateTime endTime,
    
    UUID eventId,
    
    String notes
) {}
