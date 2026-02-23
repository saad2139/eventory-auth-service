package com.eventory.inventory.dto.responses;

import java.util.UUID;
import java.time.OffsetDateTime;

public record StockResponse(
    UUID id,
    UUID tenantId,
    UUID itemId,
    UUID locationId,
    Integer quantity,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
