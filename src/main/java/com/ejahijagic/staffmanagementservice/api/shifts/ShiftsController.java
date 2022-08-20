package com.ejahijagic.staffmanagementservice.api.shifts;

import com.ejahijagic.staffmanagementservice.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.security.AuthenticationHolder;
import com.ejahijagic.staffmanagementservice.service.ShiftService;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/shifts")
public class ShiftsController {

  private final ShiftService shiftService;

  @GetMapping
  @PreAuthorize("#auth.hasPermission('SHIFTS_SEE')")
  public List<ShiftEntity> findShiftByUser(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      AuthenticationHolder auth) {
    if (Objects.isNull(userId)) {
      return shiftService.findShifts(from, to);
    } else {
      return shiftService.findShifts(userId, from, to);
    }
  }

  @PostMapping
  @PreAuthorize("#auth.hasPermission('SHIFTS_CREATE')")
  public ShiftEntity createShift(@RequestBody ShiftEntity shift, AuthenticationHolder auth) {
    return shiftService.create(shift);
  }

  @PutMapping(path = "{shiftId}")
  @PreAuthorize("#auth.hasPermission('SHIFTS_UPDATE')")
  public ShiftEntity updateShift(@PathVariable long shiftId, @RequestBody ShiftEntity shift,
      AuthenticationHolder auth) {
    return shiftService.update(shiftId, shift);
  }

  @DeleteMapping(path = "{shiftId}")
  @PreAuthorize("#auth.hasPermission('SHIFTS_DELETE')")
  public void deleteShift(@PathVariable long shiftId, AuthenticationHolder auth) {
    shiftService.delete(shiftId);
  }
}
