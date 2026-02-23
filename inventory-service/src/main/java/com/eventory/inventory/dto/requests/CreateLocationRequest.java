package com.eventory.inventory.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateLocationRequest(
    @NotBlank(message = "Name is required")
    String name,
    String address,
    String type
) {}
