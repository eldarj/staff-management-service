package com.ejahijagic.staffmanagementservice.users.data;

public enum UserRole {
  STAFF_USER("staff"), ADMIN_USER("admin");

  private final String name;

  UserRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
