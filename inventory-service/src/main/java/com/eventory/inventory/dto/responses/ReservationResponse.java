package com.eventory.inventory.dto.responses;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationResponse(
    UUID id,
    UUID tenantId,
    UUID itemId,
    UUID locationId,
    UUID eventId,
    UUID reservedByUserId,
    Integer quantity,
    OffsetDateTime startTime,
    OffsetDateTime endTime,
    String status,
    String notes,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
