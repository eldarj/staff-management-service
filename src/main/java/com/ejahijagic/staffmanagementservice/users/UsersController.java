package com.ejahijagic.staffmanagementservice.users;

import com.ejahijagic.staffmanagementservice.users.model.User;
import com.ejahijagic.staffmanagementservice.users.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public record UsersController(UserService userService) {

  @GetMapping
  public List<User> findUsers() {
    return userService.findAllUsers();
  }

  @GetMapping(path = "{userId}")
  public User findUserById(@PathVariable Long userId) {
    return userService.findUserById(userId);
  }

  @PutMapping(path = "{userId}")
  public User updateUser(@PathVariable Long userId, @RequestBody User user) {
    return userService.update(userId, user);
  }

  @DeleteMapping(path = "{userId}")
  public void deleteUser(@PathVariable Long userId) {
    this.userService.delete(userId);
  }
}
