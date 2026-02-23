package com.eventory.inventory.specs;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.eventory.inventory.entities.Stock;

public class StockSpec {
    
    public static Specification<Stock> hasTenantId(UUID tenantId){
        return (stock, query, cb) -> cb.equal(stock.get("tenantId"),tenantId);
    }
    public static Specification<Stock> hasId(UUID id){
        return (stock, query, cb) -> cb.equal(stock.get("id"),id);
    }
    public static Specification<Stock> hasItemId(UUID itemId){
        return (stock, query, cb) -> cb.equal(stock.get("itemId"),itemId);
    }
    public static Specification<Stock> hasLocationId(UUID locationId){
        return (stock, query, cb) -> cb.equal(stock.get("locationId"),locationId);
    }

}

