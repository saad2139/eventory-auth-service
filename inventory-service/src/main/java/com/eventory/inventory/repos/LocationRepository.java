package com.eventory.inventory.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eventory.inventory.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID>, JpaSpecificationExecutor<Location> {
    
    
}
