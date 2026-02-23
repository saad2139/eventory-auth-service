package com.eventory.inventory.dto;

public record UpdateLocationRequest(
    String name,
    String address,
    String type
) {
}
