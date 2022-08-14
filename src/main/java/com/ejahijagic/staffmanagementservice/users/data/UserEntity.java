package com.ejahijagic.staffmanagementservice.users.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  private UserRole role = UserRole.STAFF_USER;

  private int workingHours;

  public UserEntity(String username, UserRole role) {
    this.username = username;
    this.role = role;
  }
}
