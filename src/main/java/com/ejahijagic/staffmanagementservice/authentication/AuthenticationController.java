package com.ejahijagic.staffmanagementservice.authentication;

import com.ejahijagic.staffmanagementservice.authentication.model.RegisterUserRequest;
import com.ejahijagic.staffmanagementservice.authentication.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public record AuthenticationController(AuthenticationService authenticationService) {

  @PostMapping(value = "register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody RegisterUserRequest registerUserRequest) {
    this.authenticationService.register(registerUserRequest);
  }
}
