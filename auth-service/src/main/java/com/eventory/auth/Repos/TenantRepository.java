package com.eventory.auth.Repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventory.auth.Entities.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
   Optional<Tenant> findByName(String name);
}
