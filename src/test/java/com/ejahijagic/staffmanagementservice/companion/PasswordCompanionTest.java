package com.ejahijagic.staffmanagementservice.companion;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordCompanionTest {

  PasswordCompanion passwordCompanion = new PasswordCompanion();

  @Test
  void hashPasswordTest() {
    // given
    var plainPassword = "SomePassPhrase";

    // when
    String hashedPassword = passwordCompanion.hash(plainPassword);

    // then
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
    boolean plainMatchesHash = bCryptPasswordEncoder.matches(plainPassword, hashedPassword);

    assertNotEquals(plainPassword, hashedPassword);
    assertTrue(plainMatchesHash);
  }
}