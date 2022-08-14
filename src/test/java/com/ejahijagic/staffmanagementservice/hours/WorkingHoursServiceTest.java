package com.ejahijagic.staffmanagementservice.hours;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static com.ejahijagic.staffmanagementservice.users.data.UserRole.STAFF_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.companion.DateCompanion;
import com.ejahijagic.staffmanagementservice.hours.service.WorkingHoursService;
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
class WorkingHoursServiceTest {

  @Mock
  UserRepository userRepository;
  @Mock
  ShiftRepository shiftRepository;
  @Mock
  DateCompanion dateCompanion;

  @InjectMocks
  WorkingHoursService workingHoursService;

  @Test
  void getUsersWithWorkHoursAccumulatedTest() throws ParseException {
    // given
    var user0Mock = getUserMock(123L);
    var user1Mock = getUserMock(124L);
    var user2Mock = getUserMock(125L);

    var dateFromMock = DATE_FORMAT.parse("15-5-2022");
    var dateToMock = DATE_FORMAT.parse("15-12-2022");
    var someShiftDate = DATE_FORMAT.parse("25-6-2022");

    var shiftsMock = new ArrayList<ShiftEntity>() {{
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));
      add(new ShiftEntity(user0Mock.getId(), 8, someShiftDate));

      add(new ShiftEntity(user1Mock.getId(), 8, someShiftDate));

      add(new ShiftEntity(user2Mock.getId(), 4, someShiftDate));
    }};

    when(dateCompanion.parse("15-5-2022")).thenReturn(dateFromMock);
    when(dateCompanion.parse("15-12-2022")).thenReturn(dateToMock);
    when(shiftRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
        dateFromMock, dateToMock)).thenReturn(shiftsMock);
    when(userRepository.findById(user0Mock.getId())).thenReturn(Optional.of(user0Mock));
    when(userRepository.findById(user1Mock.getId())).thenReturn(Optional.of(user1Mock));
    when(userRepository.findById(user2Mock.getId())).thenReturn(Optional.of(user2Mock));

    // when
    List<UserEntity> users = workingHoursService.getUserShiftsAccumulated("15-5-2022",
        "15-12-2022");

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
  }

  private UserEntity getUserMock(long id) {
    return new UserEntity(id, "mock", "", STAFF_USER, 0L, false);
  }

  private Long getHours(long userId, List<UserEntity> users) {
    return users.stream().filter(user -> user.getId() == userId)
        .findFirst().orElse(null)
        .getWorkingHours();
  }
}