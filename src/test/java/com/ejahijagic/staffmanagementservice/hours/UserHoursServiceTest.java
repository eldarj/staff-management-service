package com.ejahijagic.staffmanagementservice.hours;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static com.ejahijagic.staffmanagementservice.users.data.UserRole.STAFF_USER;
import static com.github.seregamorph.hamcrest.OrderMatchers.strictOrdered;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.companion.DateCompanion;
import com.ejahijagic.staffmanagementservice.hours.service.UserHoursService;
import com.ejahijagic.staffmanagementservice.hours.service.UserHoursService.Order;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import com.ejahijagic.staffmanagementservice.users.data.UserEntity;
import com.ejahijagic.staffmanagementservice.users.data.UserRepository;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserHoursServiceTest {

  @Mock
  UserRepository userRepository;
  @Mock
  ShiftRepository shiftRepository;
  @Mock
  DateCompanion dateCompanion;

  @InjectMocks
  UserHoursService userHoursService;

  @Test
  void getUsersWithWorkHoursAccumulatedTest() throws ParseException {
    // given
    var user0Mock = getUserMock(123L);
    var user1Mock = getUserMock(124L);
    var user2Mock = getUserMock(125L);

    var dateFromMock = DATE_FORMAT.parse("2022-5-15");
    var dateToMock = DATE_FORMAT.parse("2022-12-15");
    var someShiftDate = DATE_FORMAT.parse("2022-6-25");

    var shiftsMock = new ArrayList<ShiftEntity>() {{
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));

      add(new ShiftEntity(user1Mock.getId(), 8, someShiftDate));

      add(new ShiftEntity(user2Mock.getId(), 4, someShiftDate));
    }};

    when(dateCompanion.parse("2022-5-15")).thenReturn(dateFromMock);
    when(dateCompanion.parse("2022-12-15")).thenReturn(dateToMock);
    when(shiftRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
        dateFromMock, dateToMock)).thenReturn(shiftsMock);
    when(userRepository.findById(user0Mock.getId())).thenReturn(Optional.of(user0Mock));
    when(userRepository.findById(user1Mock.getId())).thenReturn(Optional.of(user1Mock));
    when(userRepository.findById(user2Mock.getId())).thenReturn(Optional.of(user2Mock));

    // when
    List<UserEntity> users = userHoursService.findUserShiftsAccumulated(Order.DESC, "2022-5-15",
        "2022-12-15");

    // then
    var expectedHoursUserOne = 24;
    var expectedHoursUserTwo = 8;
    var expectedHoursUserThree = 4;
    var actualHoursUserOne = getHours(user0Mock.getId(), users);
    var actualHoursUserTwo = getHours(user1Mock.getId(), users);
    var actualHoursUserThree = getHours(user2Mock.getId(), users);

    assertEquals(expectedHoursUserOne, actualHoursUserOne);
    assertEquals(expectedHoursUserTwo, actualHoursUserTwo);
    assertEquals(expectedHoursUserThree, actualHoursUserThree);

    // assert descending order
    assertThat(users, strictOrdered(comparing(UserEntity::getWorkingHours, reverseOrder())));
  }

  private UserEntity getUserMock(long id) {
    return new UserEntity(id, "mock", "", STAFF_USER, 0, false);
  }

  private Integer getHours(long userId, List<UserEntity> users) {
    return users.stream()
        .filter(user -> user.getId() == userId)
        .findFirst().orElse(null)
        .getWorkingHours();
  }
}