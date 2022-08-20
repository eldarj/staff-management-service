package com.ejahijagic.staffmanagementservice.security;

import com.ejahijagic.staffmanagementservice.data.UserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public record AuthenticationHolder(UserEntity user,
                                   Set<String> permissions,
                                   GrantedAuthority role) implements Authentication {

  public boolean hasPermission(String permission) {
    return permissions.contains(permission);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(role);
  }

  @Override
  public String getName() {
    return user.getUsername();
  }

  @Override
  public Object getDetails() {
    return permissions;
  }

  @Override
  public Object getPrincipal() {
    return user;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }


  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
  }
}
