package com.ejahijagic.staffmanagementservice.exception;

import java.text.SimpleDateFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class InvalidDateException extends RuntimeException {

  private final String date;

  public InvalidDateException(String date) {
    super("Invalid date, please supply " + new SimpleDateFormat("yyyy-MM-dd").toPattern());
    this.date = date;
  }
}
