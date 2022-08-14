package com.ejahijagic.staffmanagementservice.hours.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import com.ejahijagic.staffmanagementservice.companion.DateCompanion;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public record UserHoursService(
    UserRepository userRepository, ShiftRepository shiftRepository, DateCompanion dateCompanion) {

  @Getter
  @AllArgsConstructor
  public enum Order {
    ASC("asc"), DESC("desc");
    private final String value;
  }

  public List<UserEntity> findUserShiftsAccumulated(Order order, String from, String to) {
    Comparator<Integer> comparator = getComparator(order);

    var dateFrom = dateCompanion.parse(from);
    var dateTo = dateCompanion.parse(to);

    Map<Long, Integer> hoursPerUser = getAccumulatedHoursPerUser(dateFrom, dateTo);

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

  private Map<Long, Integer> getAccumulatedHoursPerUser(Date from, Date to) {
    var shifts = shiftRepository
        .findByDateGreaterThanEqualAndDateLessThanEqual(from, to);

    var hoursSum = summingInt(ShiftEntity::getLengthHours);
    var hoursPerUser = groupingBy(
        ShiftEntity::getUserId, hoursSum);

    return shifts.stream().collect(hoursPerUser);
  }
}