package com.eventory.inventory.mappers;

import com.eventory.inventory.dto.responses.StockResponse;
import com.eventory.inventory.entities.Stock;

public class StockMapper {

    public static StockResponse toResponse(Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getTenantId(),
            stock.getItemId(),
            stock.getLocationId(),
            stock.getQuantity(),
            stock.getCreatedAt(),
            stock.getUpdatedAt()
        );
    }
}
