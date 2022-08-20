package com.ejahijagic.staffmanagementservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

  record ServiceErrorResponse(String message, Object payload) {

  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Object> onUserAlreadyExists(UserAlreadyExistsException e) {
    return new ResponseEntity<>(
        new ServiceErrorResponse(e.getMessage(), e.getUsername()),
        new HttpHeaders(),
        HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> onUserNotFound(UserNotFoundException e) {
    return new ResponseEntity<>(
        new ServiceErrorResponse(e.getMessage(), e.getUserId()),
        new HttpHeaders(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidDateException.class)
  public ResponseEntity<Object> onInvalidDate(InvalidDateException e) {
    return new ResponseEntity<>(
        new ServiceErrorResponse(e.getMessage(), e.getDate()),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(InvalidDateFilterLengthException.class)
  public ResponseEntity<Object> onInvalidDate(InvalidDateFilterLengthException e) {
    return new ResponseEntity<>(
        new ServiceErrorResponse(e.getMessage(), ""),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }
}
