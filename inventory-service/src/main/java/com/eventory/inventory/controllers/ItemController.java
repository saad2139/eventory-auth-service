package com.eventory.inventory.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventory.inventory.components.CurrentUser;
import com.eventory.inventory.services.ItemService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eventory.inventory.dto.responses.ItemResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eventory.inventory.dto.requests.CreateItemRequest;
import com.eventory.inventory.dto.requests.UpdateItemRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final CurrentUser currentUser;

    public ItemController(ItemService itemService, CurrentUser currentUser){
        this.itemService = itemService;
        this.currentUser = currentUser;
    }

    @GetMapping
    public List<ItemResponse> getAllItems(@RequestParam(required=false) String params) {
      return this.itemService.getAllItems(currentUser.getTenantId());
    }

    @GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable UUID itemId) {
        return itemService.getItemById(currentUser.getTenantId(), itemId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse postMethodName(@Valid @RequestBody CreateItemRequest req) {
       return this.itemService.createItem(currentUser.getTenantId(), req);
    }

    @PutMapping("/{itemId}")
    public ItemResponse putMethodName(@PathVariable UUID itemId, @Valid @RequestBody UpdateItemRequest req) {
       return itemService.updateItem(currentUser.getTenantId(), itemId, req);
    }
    


    
    
}
