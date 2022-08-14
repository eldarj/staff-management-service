package com.ejahijagic.staffmanagementservice.hours;


import com.ejahijagic.staffmanagementservice.hours.service.UserHoursService;
import com.ejahijagic.staffmanagementservice.hours.service.UserHoursService.Order;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hours")
public record UserHoursController(UserHoursService userHoursService) {

  @GetMapping
  public List<UserEntity> findHoursForUsers(
      @RequestParam Order order,
      @RequestParam String from,
      @RequestParam String to) {
    return userHoursService.findUserShiftsAccumulated(order, from, to);
  }
}
