package com.eventory.inventory.dto.requests;

public record UpdateLocationRequest(
    String name,
    String address,
    String type
) {
}
