package com.ejahijagic.staffmanagementservice.api.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

  private String username;

  private String password;
}
