package com.eventory.inventory.dto.responses;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ItemResponse(
    UUID id,
    UUID tenantId,
    String name,
    String description,
    String sku,
    String category,
    String imageUrl,
    String status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    
}
