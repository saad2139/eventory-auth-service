package com.eventory.inventory.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Min;

public record UpdateReservationRequest(
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity,
    OffsetDateTime startTime,
    OffsetDateTime endTime,
    String notes
) {
}
