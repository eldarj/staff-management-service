package com.ejahijagic.staffmanagementservice.users.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String username;

  private String password;

  private UserRole role = UserRole.STAFF_USER;

  private long workingHours;

  private boolean deleted;

  public UserEntity(String username, UserRole role) {
    this.username = username;
    this.role = role;
  }
}
