package com.eventory.inventory.specs;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.eventory.inventory.entities.Location;

public class LocationSpec {

    public static Specification<Location> hasTenantId(UUID tenantId){
        return (location,query,cb) -> cb.equal(location.get("tenantId"), tenantId);
    }

    public static Specification<Location> hasStatus(String status){
        return (location,query,cb) -> cb.equal(location.get("status"),status);
    }
        public static Specification<Location> hasType(String type){
        return (location,query,cb) -> type == null ? null : cb.equal(location.get("type"),type);
    }

    public static Specification<Location> nameContains(String name) {
        return (root, query, cb) -> name == null ? null : 
            cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Location> hasId(UUID id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
