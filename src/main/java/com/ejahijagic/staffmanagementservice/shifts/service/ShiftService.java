package com.ejahijagic.staffmanagementservice.shifts.service;

import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public record ShiftService(ShiftRepository shiftRepository, ShiftFilterCompanion shiftCompanion) {

  public List<ShiftEntity> findShifts(String from, String to) {
    var filter = shiftCompanion.of(from, to);
    return shiftRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
        filter.dateFrom(), filter.dateTo());
  }

  public List<ShiftEntity> findShifts(long userId, String from, String to) {
    var filter = shiftCompanion.of(from, to);
    return shiftRepository.findByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
        userId, filter.dateFrom(), filter.dateTo());
  }

  public ShiftEntity create(ShiftEntity shift) {
    return shiftRepository.save(shift);
  }

  public ShiftEntity update(long shiftId, ShiftEntity shift) {
    shift.setId(shiftId);
    return shiftRepository.save(shift);
  }

  public void delete(long shiftId) {
    shiftRepository.deleteById(shiftId);
  }
}
