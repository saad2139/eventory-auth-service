-- Create locations table for warehouse/venue management
CREATE TABLE locations (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_locations_tenant_id ON locations(tenant_id);
CREATE INDEX idx_locations_tenant_status ON locations(tenant_id, status);

COMMENT ON TABLE locations IS 'Physical locations where items are stored or used';
COMMENT ON COLUMN locations.type IS 'WAREHOUSE, VENUE, STORAGE, etc.';
COMMENT ON COLUMN locations.status IS 'ACTIVE, INACTIVE';
