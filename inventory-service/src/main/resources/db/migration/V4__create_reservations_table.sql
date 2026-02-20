-- Create reservations table for time-window based bookings
CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    item_id UUID NOT NULL,
    location_id UUID NOT NULL,
    event_id UUID,
    reserved_by_user_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    CONSTRAINT fk_reservations_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    CONSTRAINT fk_reservations_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE,
    CONSTRAINT check_quantity_positive CHECK (quantity > 0),
    CONSTRAINT check_end_after_start CHECK (end_time > start_time)
);

CREATE INDEX idx_reservations_tenant_id ON reservations(tenant_id);
CREATE INDEX idx_reservations_item_location ON reservations(tenant_id, item_id, location_id);
CREATE INDEX idx_reservations_time_overlap ON reservations(tenant_id, item_id, location_id, start_time, end_time);
CREATE INDEX idx_reservations_event_id ON reservations(tenant_id, event_id);
CREATE INDEX idx_reservations_user_id ON reservations(tenant_id, reserved_by_user_id);
CREATE INDEX idx_reservations_status ON reservations(tenant_id, status);

COMMENT ON TABLE reservations IS 'Time-window based bookings for items at locations';
COMMENT ON COLUMN reservations.status IS 'PENDING, CONFIRMED, CANCELLED, COMPLETED';
COMMENT ON COLUMN reservations.event_id IS 'Future: link to event service';
COMMENT ON CONSTRAINT check_end_after_start ON reservations IS 'End time must be after start time';
