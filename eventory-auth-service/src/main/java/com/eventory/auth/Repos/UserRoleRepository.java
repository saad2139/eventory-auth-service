package com.eventory.auth.Repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventory.auth.Entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Object> {
  List<UserRole> findByUserId(UUID userId);
}
