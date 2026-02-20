-- Create stock table to track item quantities at locations
CREATE TABLE stock (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    item_id UUID NOT NULL,
    location_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    CONSTRAINT fk_stock_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    CONSTRAINT fk_stock_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE,
    CONSTRAINT unique_item_location_per_tenant UNIQUE (tenant_id, item_id, location_id),
    CONSTRAINT check_quantity_non_negative CHECK (quantity >= 0)
);

CREATE INDEX idx_stock_tenant_id ON stock(tenant_id);
CREATE INDEX idx_stock_item_id ON stock(tenant_id, item_id);
CREATE INDEX idx_stock_location_id ON stock(tenant_id, location_id);

COMMENT ON TABLE stock IS 'Tracks quantity of items available at each location';
COMMENT ON CONSTRAINT check_quantity_non_negative ON stock IS 'Quantity cannot be negative';
