package com.ejahijagic.staffmanagementservice.service;

import com.ejahijagic.staffmanagementservice.data.UserEntity;
import com.ejahijagic.staffmanagementservice.data.UserRepository;
import com.ejahijagic.staffmanagementservice.data.UserRole;
import com.ejahijagic.staffmanagementservice.exception.UserNotFoundException;
import com.ejahijagic.staffmanagementservice.api.users.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public record UserService(UserRepository userRepository, ObjectMapper objectMapper) {

  public List<User> findAllUsers() {
    return userRepository.findAll().stream()
        .map(user -> objectMapper.convertValue(user, User.class))
        .toList();
  }

  public User findUserById(Long userId) {
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    return objectMapper.convertValue(user, User.class);
  }

  public User create(UserEntity user) {
    return objectMapper.convertValue(userRepository.save(user), User.class);
  }

  public User update(long userId, User user) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    userEntity.setUsername(user.getUsername());
    userEntity.setRole(UserRole.of(user.getRole()));

    return objectMapper.convertValue(userRepository.save(userEntity), User.class);
  }

  public void delete(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }

    userRepository.deleteById(userId);
  }
}
