package com.ejahijagic.staffmanagementservice.service;

import com.ejahijagic.staffmanagementservice.data.UserEntity;
import com.ejahijagic.staffmanagementservice.api.authentication.model.RegisterUserRequest;
import com.ejahijagic.staffmanagementservice.companion.PasswordCompanion;
import com.ejahijagic.staffmanagementservice.exception.UserAlreadyExistsException;
import com.ejahijagic.staffmanagementservice.data.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public record AuthenticationService(UserRepository userRepository, PasswordCompanion companion) {

  public void register(RegisterUserRequest registerUserRequest) {
    UserEntity user = of(registerUserRequest);
    try {
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new UserAlreadyExistsException(user.getUsername());
    }
  }

  private UserEntity of(RegisterUserRequest userRequest) {
    UserEntity user = new UserEntity();
    user.setUsername(userRequest.getUsername());
    user.setPassword(companion.hash(userRequest.getPassword()));

    return user;
  }
}
