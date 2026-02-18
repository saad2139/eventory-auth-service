CREATE TABLE tenants (
  id UUID PRIMARY KEY,
  name VARCHAR(200) NOT NULL UNIQUE,
  status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE users (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL REFERENCES tenants(id),
  email VARCHAR(320) NOT NULL UNIQUE,
  password_hash VARCHAR(200) NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE user_roles (
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  role VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id, role)
);

CREATE INDEX idx_users_tenant_id ON users(tenant_id);
CREATE INDEX idx_roles_user_id ON user_roles(user_id);
