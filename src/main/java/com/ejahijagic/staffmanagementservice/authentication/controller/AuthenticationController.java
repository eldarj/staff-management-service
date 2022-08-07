package com.ejahijagic.staffmanagementservice.authentication.controller;

import com.ejahijagic.staffmanagementservice.authentication.service.AuthenticationService;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  public Map<String, String> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String basicAuth) {
    return null;
  }

  public Map<String, String> register() {
    return null;
  }
}
