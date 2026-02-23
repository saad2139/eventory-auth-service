package com.eventory.inventory.mappers;

import com.eventory.inventory.dto.responses.LocationResponse;
import com.eventory.inventory.entities.Location;

public class LocationMapper {

    public static LocationResponse toResponse(Location location) {
        return new LocationResponse(
            location.getId(),
            location.getTenantId(),
            location.getName(),
            location.getType(),
            location.getStatus(),
            location.getCreatedAt(),
            location.getUpdatedAt()
        );
    }
}
