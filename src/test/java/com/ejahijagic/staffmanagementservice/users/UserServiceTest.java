package com.ejahijagic.staffmanagementservice.users;

import static com.ejahijagic.staffmanagementservice.data.UserRole.ADMIN_USER;
import static com.ejahijagic.staffmanagementservice.data.UserRole.STAFF_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.api.users.model.User;
import com.ejahijagic.staffmanagementservice.data.UserEntity;
import com.ejahijagic.staffmanagementservice.data.UserRepository;
import com.ejahijagic.staffmanagementservice.exception.UserNotFoundException;
import com.ejahijagic.staffmanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  ObjectMapper objectMapper;

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService;

  @Test
  void findAllUsersTest() {
    // given
    List<UserEntity> usersMock = new ArrayList<>() {{
      add(new UserEntity("eldar", ADMIN_USER));
      add(new UserEntity("patric", ADMIN_USER));
      add(new UserEntity("john", STAFF_USER));
    }};

    when(userRepository.findAll()).thenReturn(usersMock);

    // when
    List<User> users = userService.findAllUsers();

    // then
    verify(userRepository, times(1)).findAll();
    assertEquals(usersMock.size(), users.size());
  }

  @Test
  void createTest() {
    // given
    UserEntity user = new UserEntity("eldar", ADMIN_USER);
    User expectedUser = new User(1, "eldar", ADMIN_USER.getName());
    when(userRepository.save(user)).thenReturn(user);
    when(objectMapper.convertValue(user, User.class)).thenReturn(expectedUser);

    // when
    User createdUser = userService.create(user);

    verify(userRepository, times(1)).save(user);
    assertEquals(user.getUsername(), createdUser.getUsername());
  }

  @Test
  void editUserTest() {
    // given
    var userEntity = new UserEntity("eldar", STAFF_USER);
    userEntity.setId(123L);
    var user = new User(userEntity.getId(), "ejahijagic", STAFF_USER.getName());

    when(userRepository.findById(123L)).thenReturn(Optional.of(userEntity));
    userEntity.setUsername(user.getUsername());
    when(userRepository.save(userEntity)).thenReturn(userEntity);
    when(objectMapper.convertValue(userEntity, User.class)).thenReturn(user);

    // when
    User updatedUser = userService.update(123L, user);

    // then
    verify(userRepository, times(1)).save(userEntity);
    assertEquals(userEntity.getUsername(), updatedUser.getUsername());
  }

  @Test
  void deleteUserTest() {
    // given
    var userId = 123L;
    when(userRepository.existsById(userId)).thenReturn(true);

    // when
    userService.delete(123L);

    verify(userRepository, times(1)).existsById(123L);
    verify(userRepository, times(1)).deleteById(123L);
  }

  @Test
  void deleteUserWhichDoesntExistTest() {
    // given
    var userId = 123L;

    // when
    Exception exception = assertThrows(Exception.class, () -> {
      userService.delete(userId);
    });

    // then
    String expectedMessage = "User not found";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
    assertEquals(exception.getClass(), UserNotFoundException.class);
  }
}