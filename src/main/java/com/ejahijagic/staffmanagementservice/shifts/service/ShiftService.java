package com.ejahijagic.staffmanagementservice.shifts.service;

import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public record ShiftService(ShiftRepository shiftRepository) {

  public List<ShiftEntity> findAllByUser(long userId) {
    return shiftRepository.findByUserId(userId);
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
