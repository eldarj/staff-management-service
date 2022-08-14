package com.ejahijagic.staffmanagementservice.shifts;

import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.service.ShiftService;
import java.util.List;
import java.util.Objects;
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
@RequestMapping("api/shifts")
public record ShiftsController(ShiftService shiftService) {

  @GetMapping
  public List<ShiftEntity> findShiftByUser(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to) {
    if (Objects.isNull(userId)) {
      return shiftService.findShifts(from, to);
    } else {
      return shiftService.findShifts(userId, from, to);
    }
  }

  @PostMapping
  public ShiftEntity createShift(@RequestBody ShiftEntity shift) {
    return shiftService.create(shift);
  }

  @PutMapping(path = "{shiftId}")
  public ShiftEntity updateShift(@PathVariable long shiftId, @RequestBody ShiftEntity shift) {
    return shiftService.update(shiftId, shift);
  }

  @DeleteMapping(path = "{shiftId}")
  public void deleteShift(@PathVariable long shiftId) {
    shiftService.delete(shiftId);
  }
}
