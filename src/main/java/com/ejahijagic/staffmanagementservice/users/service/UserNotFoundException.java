package com.ejahijagic.staffmanagementservice.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  private static final String message = "User not found";

  public UserNotFoundException() {
    super(message);
  }
}
