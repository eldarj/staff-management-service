package com.ejahijagic.staffmanagementservice.shifts;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.exception.InvalidDateFilterLengthException;
import com.ejahijagic.staffmanagementservice.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.data.ShiftRepository;
import com.ejahijagic.staffmanagementservice.companion.ShiftFilterCompanion;
import com.ejahijagic.staffmanagementservice.companion.ShiftFilterCompanion.ShiftFilter;
import com.ejahijagic.staffmanagementservice.service.ShiftService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShiftServiceTest {

  @Mock
  ShiftRepository shiftRepository;

  @Mock
  ShiftFilterCompanion shiftFilterCompanion;

  @InjectMocks
  ShiftService shiftService;

  @Test
  void cannotViewShiftsForPeriodLargerThan1YearTest() {
    // given
    var userId = 123L;
    var dateFrom = "10-2-2020";
    var dateTo = "20-12-2022";

    doThrow(new InvalidDateFilterLengthException()).when(shiftFilterCompanion).of(dateFrom, dateTo);

    // when
    Exception exception = assertThrows(InvalidDateFilterLengthException.class, () -> {
      shiftService.findShifts(userId, dateFrom, dateTo);
    });

    String expectedMessage = "Supplied filter date span cannot be larger than a year";
    String actualMessage = exception.getMessage();

    // then
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void viewShifts() throws ParseException {
    // given
    long userId = 123L;
    var from = "10-5-2022";
    var to = "20-9-2022";
    Date dateFrom = DATE_FORMAT.parse(from);
    Date dateTo = DATE_FORMAT.parse(to);
    var shiftFilter = new ShiftFilter(dateFrom, dateTo);

    when(shiftFilterCompanion.of(from, to)).thenReturn(shiftFilter);

    List<ShiftEntity> shiftsMock = new ArrayList<>() {{
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("2022-08-15")));
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("2022-08-16")));
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("2022-08-17")));
    }};

    when(shiftRepository.findByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
        userId, dateFrom, dateTo)).thenReturn(shiftsMock);

    // when
    List<ShiftEntity> actualShifts = shiftService.findShifts(userId, from, to);

    // then
    verify(shiftRepository, times(1))
        .findByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(123L, dateFrom, dateTo);
    assertEquals(shiftsMock, actualShifts);
  }

  @Test
  void createShiftTest() throws ParseException {
    // given
    ShiftEntity shift = new ShiftEntity(123L, 8, DATE_FORMAT.parse("2022-08-15"));
    when(shiftRepository.save(shift)).thenReturn(shift);

    // when
    ShiftEntity actualShift = shiftService.create(shift);

    // then
    verify(shiftRepository, times(1)).save(shift);
    assertEquals(shift, actualShift);
  }

  @Test
  void editShiftTest() throws ParseException {
    // given
    ShiftEntity shift = new ShiftEntity(123L, 8, DATE_FORMAT.parse("2022-08-15"));
    shift.setId(321L);

    when(shiftRepository.save(shift)).thenReturn(shift);

    // when
    ShiftEntity updatedShift = shiftService.update(321L, shift);

    // then
    verify(shiftRepository, times(1)).save(shift);
    assertEquals(shift, updatedShift);
  }

  @Test
  void deleteShiftTest() {
    // given
    var shiftId = 321L;
    doNothing().when(shiftRepository).deleteById(shiftId);

    // when
    shiftService.delete(shiftId);

    // then
    verify(shiftRepository, times(1)).deleteById(shiftId);
  }
}