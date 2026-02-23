package com.eventory.inventory.mappers;

import com.eventory.inventory.dto.responses.ItemResponse;
import com.eventory.inventory.entities.Item;

public class ItemMapper {

    public static ItemResponse toResponse(Item item) {
        return new ItemResponse(
            item.getId(),
            item.getTenantId(),
            item.getName(),
            item.getDescription(),
            item.getSku(),
            item.getCategory(),
            item.getImageUrl(),
            item.getStatus(),
            item.getCreatedAt(),
            item.getUpdatedAt()
        );
    }
}
