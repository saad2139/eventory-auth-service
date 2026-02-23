package com.eventory.inventory.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LocationResponse(    
    UUID id,
    UUID tenantId,
    String name,
    String address,
    String type,
    String status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt) {
    
}
