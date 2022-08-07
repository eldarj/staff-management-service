package com.ejahijagic.staffmanagementservice.users.service;

import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public record UserService(UserRepository userRepository) {

  public List<UserEntity> findAllUsers() {
    return userRepository.findAll();
  }

  public UserEntity create(UserEntity user) {
    return userRepository.save(user);
  }

  public UserEntity update(long userId, UserEntity user) {
    user.setId(userId);
    return userRepository.save(user);
  }

  public void softDelete(long userId) {
    UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    user.setDeleted(true);

    userRepository.save(user);
  }
}
