package com.ejahijagic.staffmanagementservice.authentication.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {


  void authenticateTest() {
    // given
    // legit basic auth header

    // when
    // invoking authenticate on service

    // then
    // verify basic auth is matched against username:password on UserEntity
    // verify simple jwt token is created and returned
  }

  void registerTest() {
    // given
    // a legit post request with UserEntity

    // when
    // invoking register on service

    // then
    // verify user is created
  }
}