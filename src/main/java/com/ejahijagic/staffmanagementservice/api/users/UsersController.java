package com.ejahijagic.staffmanagementservice.api.users;

import com.ejahijagic.staffmanagementservice.api.users.model.User;
import com.ejahijagic.staffmanagementservice.security.AuthenticationHolder;
import com.ejahijagic.staffmanagementservice.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UsersController {

  private final UserService userService;

  @GetMapping
  @PreAuthorize("#auth.hasPermission('USERS_SEE')")
  public List<User> findUsers(AuthenticationHolder auth) {
    return userService.findAllUsers();
  }

  @GetMapping(path = "{userId}")
  @PreAuthorize("#auth.hasPermission('USERS_SEE')")
  public User findUserById(@PathVariable Long userId, AuthenticationHolder auth) {
    return userService.findUserById(userId);
  }

  @PutMapping(path = "{userId}")
  @PreAuthorize("#auth.hasPermission('USERS_UPDATE')")
  public User updateUser(@PathVariable Long userId, @RequestBody User user,
      AuthenticationHolder auth) {
    return userService.update(userId, user);
  }

  @DeleteMapping(path = "{userId}")
  @PreAuthorize("#auth.hasPermission('USERS_DELETE')")
  public void deleteUser(@PathVariable Long userId, AuthenticationHolder auth) {
    this.userService.delete(userId);
  }
}
