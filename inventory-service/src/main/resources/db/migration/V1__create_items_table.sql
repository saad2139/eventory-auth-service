-- Create items table for inventory management
CREATE TABLE items (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(100),
    category VARCHAR(100),
    image_url VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    CONSTRAINT unique_sku_per_tenant UNIQUE (tenant_id, sku)
);

CREATE INDEX idx_items_tenant_id ON items(tenant_id);
CREATE INDEX idx_items_tenant_status ON items(tenant_id, status);

COMMENT ON TABLE items IS 'Products/resources that can be tracked and reserved';
COMMENT ON COLUMN items.status IS 'ACTIVE, INACTIVE, ARCHIVED';
COMMENT ON COLUMN items.sku IS 'Stock Keeping Unit - unique per tenant';
