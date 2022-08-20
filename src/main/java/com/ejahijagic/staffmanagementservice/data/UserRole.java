package com.ejahijagic.staffmanagementservice.data;

import java.util.Objects;

public enum UserRole {
  STAFF_USER("staff"), ADMIN_USER("admin");

  private final String name;

  UserRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static UserRole of(String role) {
    if (Objects.equals(role, "staff")) {
      return STAFF_USER;
    } else if (Objects.equals(role, "admin")) {
      return ADMIN_USER;
    }

    throw new IllegalArgumentException("No enum found for " + role);
  }
}
