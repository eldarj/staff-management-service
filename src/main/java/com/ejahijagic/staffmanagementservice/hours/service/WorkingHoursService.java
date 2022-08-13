package com.ejahijagic.staffmanagementservice.hours.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import com.ejahijagic.staffmanagementservice.companion.DateCompanion;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public record WorkingHoursService(UserRepository userRepository,
                                  ShiftRepository shiftRepository,
                                  DateCompanion dateCompanion) {

  public List<UserEntity> getUsersWithWorkHoursAccumulated(String fromStr) {
    var from = dateCompanion.parse(fromStr);

    List<ShiftEntity> shifts = shiftRepository.findByDateGreaterThanEqual(from);
    Map<Long, Long> hoursPerUser = getAccumulatedHoursPerUser(shifts);

    return hoursPerUser.entrySet().stream().map(entry -> {
      var userId = entry.getKey();
      var user = userRepository.findById(userId).get();
      user.setWorkingHours(entry.getValue());
      return user;
    }).toList();
  }

  private Map<Long, Long> getAccumulatedHoursPerUser(List<ShiftEntity> shifts) {
    var hoursSum = summingLong(ShiftEntity::getLengthHours);
    var hoursPerUser = groupingBy(
        ShiftEntity::getUserId, hoursSum);

    return shifts.stream().collect(hoursPerUser);
  }
}
