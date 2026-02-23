package com.eventory.inventory.mappers;

import com.eventory.inventory.dto.responses.ReservationResponse;
import com.eventory.inventory.entities.Reservation;

public class ReservationMapper {

    public static ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getTenantId(),
            reservation.getItemId(),
            reservation.getLocationId(),
            reservation.getEventId(),
            reservation.getReservedByUserId(),
            reservation.getQuantity(),
            reservation.getStartTime(),
            reservation.getEndTime(),
            reservation.getStatus(),
            reservation.getNotes(),
            reservation.getCreatedAt(),
            reservation.getUpdatedAt()
        );
    }
}
