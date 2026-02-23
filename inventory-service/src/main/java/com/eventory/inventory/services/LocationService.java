package com.eventory.inventory.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.eventory.inventory.dto.requests.CreateLocationRequest;
import com.eventory.inventory.dto.responses.LocationResponse;
import com.eventory.inventory.entities.Location;
import com.eventory.inventory.mappers.LocationMapper;
import com.eventory.inventory.repos.LocationRepository;
import com.eventory.inventory.specs.LocationSpec;

@Service
public class LocationService {

    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    
    public List<LocationResponse> getAllLocations(UUID tenantId){
        return this.locationRepo.findAll(LocationSpec.hasTenantId(tenantId)).stream().map(LocationMapper::toResponse).toList();
    }

    public LocationResponse getLocationById(UUID tenantId, UUID locationId){
        var location = this.locationRepo.findOne(Specification.where(LocationSpec.hasTenantId(tenantId)).and(LocationSpec.hasId(locationId))).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with that Id not found"));
        return LocationMapper.toResponse(location);
    }

    public List<LocationResponse> getActiveLocations(UUID tenantId){
        return this.locationRepo.findAll(Specification.where(LocationSpec.hasTenantId(tenantId)).and(LocationSpec.hasStatus("ACTIVE"))).stream().map(LocationMapper::toResponse).toList();
    }

    public List<LocationResponse> getLocationsByType(UUID tenantId, String type){
        return this.locationRepo.findAll(Specification.where(LocationSpec.hasTenantId(tenantId)).and(LocationSpec.hasType(type))).stream().map(LocationMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LocationResponse> searchLocationsByName(UUID tenantId, String name) {
        return locationRepo.findAll(Specification.where(LocationSpec.hasTenantId(tenantId)).and(LocationSpec.nameContains(name))).stream().map(LocationMapper::toResponse).toList();
    }

    @Transactional()
    public LocationResponse createLocation(UUID tenantId, CreateLocationRequest body){
        Location location = new Location();

        location.setTenantId(tenantId);
        location.setName(body.name());
        location.setType(body.type());
        
        return LocationMapper.toResponse(locationRepo.save(location));

    }
    
}
