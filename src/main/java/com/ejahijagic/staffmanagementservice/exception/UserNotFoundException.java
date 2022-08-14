package com.ejahijagic.staffmanagementservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  private static final String message = "User not found";

  private final Long userId;

  public UserNotFoundException(Long userId) {
    super(message);
    this.userId = userId;
  }
}
