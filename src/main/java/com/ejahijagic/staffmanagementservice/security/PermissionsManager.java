package com.ejahijagic.staffmanagementservice.security;

import static com.ejahijagic.staffmanagementservice.data.UserRole.ADMIN_USER;
import static com.ejahijagic.staffmanagementservice.data.UserRole.STAFF_USER;

import com.ejahijagic.staffmanagementservice.data.UserRole;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PermissionsManager {

  private static final Set<String> staffUserPermissions = Set.of("SHIFTS_SEE");

  private static final Set<String> adminUserPermissions = Set.of(
      "SHIFTS_SEE", "SHIFTS_CREATE", "SHIFTS_EDIT", "SHIFTS_DELETE",
      "USERS_SEE", "USERS_CREATE", "USERS_DELETE", "USERS_EDIT",
      "SHIFT_HOURS_SEE");

  private static final Map<UserRole, Set<String>> PERMISSIONS = Map.of(
      STAFF_USER, staffUserPermissions,
      ADMIN_USER, adminUserPermissions
  );

  public Set<String> resolve(UserRole role) {
    return PERMISSIONS.get(role);
  }
}
