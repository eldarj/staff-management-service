package com.ejahijagic.staffmanagementservice.authentication;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.authentication.model.RegisterUserRequest;
import com.ejahijagic.staffmanagementservice.authentication.service.AuthenticationService;
import com.ejahijagic.staffmanagementservice.companion.PasswordCompanion;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  @Mock
  PasswordCompanion passwordCompanion;

  @Mock
  UserRepository userRepository;

  @InjectMocks
  AuthenticationService authenticationService;

  @Test
  void registerTest() {
    // given
    when(passwordCompanion.hash("password")).thenReturn("someHash");
    var registerUserRequest = new RegisterUserRequest("eldar", "password");

    // when
    authenticationService.register(registerUserRequest);

    // then
    var expectedUserEntity = new UserEntity();
    expectedUserEntity.setUsername("eldar");
    expectedUserEntity.setPassword("someHash");

    verify(userRepository, times(1)).save(expectedUserEntity);
    verify(passwordCompanion, times(1)).hash("password");
  }
}