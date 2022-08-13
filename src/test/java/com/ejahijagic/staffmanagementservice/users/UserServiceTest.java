package com.ejahijagic.staffmanagementservice.users;

import static com.ejahijagic.staffmanagementservice.users.data.UserRole.ADMIN_USER;
import static com.ejahijagic.staffmanagementservice.users.data.UserRole.STAFF_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import com.ejahijagic.staffmanagementservice.users.service.UserNotFoundException;
import com.ejahijagic.staffmanagementservice.users.service.UserService;
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
    List<UserEntity> users = userService.findAllUsers();

    // then
    verify(userRepository, times(1)).findAll();
    assertEquals(usersMock.size(), users.size());
  }

  @Test
  void createTest() {
    // given
    UserEntity user = new UserEntity("eldar", ADMIN_USER);
    when(userRepository.save(user)).thenReturn(user);

    // when
    UserEntity createdUser = userService.create(user);

    verify(userRepository, times(1)).save(user);
    assertEquals(user, createdUser);
  }

  @Test
  void editUserTest() {
    // given
    UserEntity user = new UserEntity("eldar", STAFF_USER);
    user.setId(123L);

    when(userRepository.save(user)).thenReturn(user);

    // when
    UserEntity updatedUser = userService.update(123L, user);

    // then
    verify(userRepository, times(1)).save(user);
    assertEquals(user, updatedUser);
  }

  @Test
  void deleteUserTest() {
    // given
    var userId = 123L;
    var userMock = new UserEntity("eldar", ADMIN_USER);
    when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));

    // when
    userService.softDelete(123L);

    verify(userRepository, times(1)).findById(123L);
    verify(userRepository, times(1)).save(userMock);
  }

  @Test
  void deleteUserWhichDoesntExistTest() {
    // given
    var userId = 123L;

    // when
    Exception exception = assertThrows(Exception.class, () -> {
      userService.softDelete(userId);
    });

    // then
    String expectedMessage = "User not found";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
    assertEquals(exception.getClass(), UserNotFoundException.class);
  }
}