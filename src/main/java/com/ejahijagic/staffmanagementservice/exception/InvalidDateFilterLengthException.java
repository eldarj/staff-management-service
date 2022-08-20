package com.ejahijagic.staffmanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class InvalidDateFilterLengthException extends RuntimeException {

  public InvalidDateFilterLengthException() {
    super("Supplied filter date span cannot be larger than a year");
  }
}