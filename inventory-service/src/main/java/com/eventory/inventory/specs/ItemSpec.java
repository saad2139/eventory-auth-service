package com.eventory.inventory.specs;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.eventory.inventory.entities.Item;

public class ItemSpec {

    public static Specification<Item> hasTenantId(UUID tenantId) {
        return (item, query, cb) -> cb.equal(item.get("tenantId"), tenantId);
    }

    public static Specification<Item> hasStatus(String status) {
        return (item, query, cb) -> status == null ? null : cb.equal(item.get("status"), status);
    }

    public static Specification<Item> hasCategory(String category) {
        return (item, query, cb) -> category == null ? null : cb.equal(item.get("category"), category);
    }

    public static Specification<Item> hasSku(String sku) {
        return (item, query, cb) -> sku == null ? null : cb.equal(item.get("sku"), sku);
    }

    public static Specification<Item> nameContains(String name) {
        return (item, query, cb) -> name == null ? null : 
            cb.like(cb.lower(item.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Item> hasId(UUID id) {
        return (item, query, cb) -> id == null ? null : cb.equal(item.get("id"), id);
    }
}