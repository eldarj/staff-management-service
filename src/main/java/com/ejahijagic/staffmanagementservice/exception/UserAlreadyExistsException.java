package com.ejahijagic.staffmanagementservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

  private static final String message = "User already exists with same username";

  private final String username;

  public UserAlreadyExistsException(String username) {
    super(message);
    this.username = username;
  }
}
