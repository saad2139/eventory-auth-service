package com.eventory.inventory.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.eventory.inventory.dto.requests.CreateItemRequest;
import com.eventory.inventory.dto.responses.ItemResponse;
import com.eventory.inventory.dto.requests.UpdateItemRequest;
import com.eventory.inventory.entities.Item;
import com.eventory.inventory.mappers.ItemMapper;
import com.eventory.inventory.repos.ItemRepository;
import com.eventory.inventory.specs.ItemSpec;

@Service
public class ItemService {

    private final ItemRepository itemrepository;

    public ItemService(ItemRepository itemrepository) {
        this.itemrepository = itemrepository;
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getAllItems(UUID tenantId) {
        return itemrepository.findAll(ItemSpec.hasTenantId(tenantId))
            .stream()
            .map(ItemMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getActiveItems(UUID tenantId) {
        return itemrepository.findAll(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasStatus("ACTIVE"))
        ).stream()
         .map(ItemMapper::toResponse)
         .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsByCategory(UUID tenantId, String category) {
        return itemrepository.findAll(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasCategory(category))
        ).stream()
         .map(ItemMapper::toResponse)
         .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponse getItemById(UUID tenantId, UUID itemId) {
        Item item = itemrepository.findOne(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasId(itemId))
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        
        return ItemMapper.toResponse(item);
    }

    @Transactional(readOnly = true)
    public ItemResponse getItemBySku(UUID tenantId, String sku) {
        Item item = itemrepository.findOne(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasSku(sku))
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        
        return ItemMapper.toResponse(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> searchItemsByName(UUID tenantId, String name) {
        return itemrepository.findAll(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.nameContains(name))
        ).stream()
         .map(ItemMapper::toResponse)
         .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse createItem(UUID tenantId, CreateItemRequest request) {
        // Validate SKU uniqueness
        if (request.sku() != null) {
            boolean exists = itemrepository.exists(
                Specification.where(ItemSpec.hasTenantId(tenantId))
                            .and(ItemSpec.hasSku(request.sku()))
            );
            if (exists) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU already exists");
            }
        }

        Item item = new Item();
        item.setTenantId(tenantId);
        item.setName(request.name());
        item.setDescription(request.description());
        item.setSku(request.sku());
        item.setCategory(request.category());
        item.setImageUrl(request.imageUrl());
        item.setStatus("ACTIVE");
        item.setCreatedAt(OffsetDateTime.now());
        item.setUpdatedAt(OffsetDateTime.now());

        Item saved = itemrepository.save(item);
        return ItemMapper.toResponse(saved);
    }

    @Transactional
    public ItemResponse updateItem(UUID tenantId, UUID itemId, UpdateItemRequest request) {
        Item item = itemrepository.findOne(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasId(itemId))
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        // Check SKU uniqueness if changed
        if (request.sku() != null && !request.sku().equals(item.getSku())) {
            boolean exists = itemrepository.exists(
                Specification.where(ItemSpec.hasTenantId(tenantId))
                            .and(ItemSpec.hasSku(request.sku()))
            );
            if (exists) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU already exists");
            }
        }

        if (request.name() != null) item.setName(request.name());
        if (request.description() != null) item.setDescription(request.description());
        if (request.sku() != null) item.setSku(request.sku());
        if (request.category() != null) item.setCategory(request.category());
        if (request.imageUrl() != null) item.setImageUrl(request.imageUrl());
        item.setUpdatedAt(OffsetDateTime.now());

        Item saved = itemrepository.save(item);
        return ItemMapper.toResponse(saved);
    }

    @Transactional
    public void deleteItem(UUID tenantId, UUID itemId) {
        Item item = itemrepository.findOne(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasId(itemId))
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        
        item.setStatus("ARCHIVED");
        item.setUpdatedAt(OffsetDateTime.now());
        itemrepository.save(item);
    }

    @Transactional
    public void hardDeleteItem(UUID tenantId, UUID itemId) {
        Item item = itemrepository.findOne(
            Specification.where(ItemSpec.hasTenantId(tenantId))
                        .and(ItemSpec.hasId(itemId))
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        
        itemrepository.delete(item);
    }
}