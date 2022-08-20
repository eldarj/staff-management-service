package com.ejahijagic.staffmanagementservice.companion;


import java.security.SecureRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordCompanion {

  private final PasswordEncoder encoder; {
    encoder = new BCryptPasswordEncoder(10, new SecureRandom());
  }

  public String hash(String password) {
    return encoder.encode(password);
  }

  public boolean matches(String plain, String hash) {
    return encoder.matches(plain, hash);
  }
}
