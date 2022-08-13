package com.ejahijagic.staffmanagementservice.authentication.service;

import com.ejahijagic.staffmanagementservice.authentication.data.RegisterUserRequest;
import com.ejahijagic.staffmanagementservice.companion.PasswordCompanion;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import org.springframework.stereotype.Component;

@Component
public record AuthenticationService(UserRepository userRepository, PasswordCompanion companion) {

  public void register(RegisterUserRequest registerUserRequest) {
    UserEntity user = of(registerUserRequest);
    userRepository.save(user);
  }

  private UserEntity of(RegisterUserRequest userRequest) {
    UserEntity user = new UserEntity();
    user.setUsername(userRequest.getUsername());
    user.setPassword(companion.hash(userRequest.getPassword()));

    return user;
  }
}
