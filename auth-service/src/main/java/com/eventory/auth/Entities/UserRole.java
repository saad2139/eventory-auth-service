package com.eventory.auth.Entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

class UserRoleId implements Serializable {
  private UUID userId;
  private String role;

  public UserRoleId() {}
  public UserRoleId(String role) {
    this.role = role;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserRoleId)) return false;
    UserRoleId that = (UserRoleId) o;
    return Objects.equals(userId, that.userId) && Objects.equals(role, that.role);
  }
  @Override public int hashCode() { return Objects.hash(userId, role); }
}

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRole {
  @Id
  @Column(name = "user_id", nullable = false)
  @GeneratedValue(strategy=GenerationType.UUID)
  private UUID userId;

  @Id
  @Column(nullable = false)
  private String role;

  public UUID getUserId() { return userId; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
}
