package com.ejahijagic.staffmanagementservice.hours.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import com.ejahijagic.staffmanagementservice.shifts.service.ShiftFilterCompanion;
import com.ejahijagic.staffmanagementservice.shifts.service.ShiftFilterCompanion.ShiftFilter;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public record UserHoursService(
    UserRepository userRepository,
    ShiftRepository shiftRepository,
    ShiftFilterCompanion shiftCompanion) {

  @Getter
  @AllArgsConstructor
  public enum Order {
    ASC("asc"), DESC("desc");
    private final String value;
  }

  public List<UserEntity> findUserShiftsAccumulated(Order order, String from, String to) {
    Comparator<Integer> comparator = getComparator(order);

    var shiftFilter = shiftCompanion.of(from, to);

    Map<Long, Integer> hoursPerUser = getAccumulatedHoursPerUser(shiftFilter);

    return hoursPerUser.entrySet().stream().map(entry -> {
      var userId = entry.getKey();
      var user = userRepository.findById(userId).get();
      user.setWorkingHours(entry.getValue());
      return user;
    }).sorted(
        comparing(UserEntity::getWorkingHours, comparator)
    ).toList();
  }

  private Comparator<Integer> getComparator(Order order) {
    return Order.DESC.equals(order) ? reverseOrder() : naturalOrder();
  }

  private Map<Long, Integer> getAccumulatedHoursPerUser(ShiftFilter filter) {
    var shifts = shiftRepository
        .findByDateGreaterThanEqualAndDateLessThanEqual(filter.dateFrom(), filter.dateTo());

    var hoursSum = summingInt(ShiftEntity::getLengthHours);
    var hoursPerUser = groupingBy(
        ShiftEntity::getUserId, hoursSum);

    return shifts.stream().collect(hoursPerUser);
  }
}
