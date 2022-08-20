package com.ejahijagic.staffmanagementservice.api.hours;


import com.ejahijagic.staffmanagementservice.data.UserEntity;
import com.ejahijagic.staffmanagementservice.security.AuthenticationHolder;
import com.ejahijagic.staffmanagementservice.service.UserHoursService;
import com.ejahijagic.staffmanagementservice.service.UserHoursService.Order;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/hours")
public class UserHoursController {

  private final UserHoursService userHoursService;

  @GetMapping
  @PreAuthorize("#auth.hasPermission('SHIFT_HOURS_SEE')")
  public List<UserEntity> findHoursForUsers(
      @RequestParam(required = false) Order order,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      AuthenticationHolder auth) {
    return userHoursService.findUserShiftsAccumulated(order, from, to);
  }
}
