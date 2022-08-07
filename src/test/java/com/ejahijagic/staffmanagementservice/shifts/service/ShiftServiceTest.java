package com.ejahijagic.staffmanagementservice.shifts.service;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ejahijagic.staffmanagementservice.shifts.data.ShiftEntity;
import com.ejahijagic.staffmanagementservice.shifts.data.ShiftRepository;
import java.text.ParseException;
import java.util.ArrayList;
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

  @InjectMocks
  ShiftService shiftService;

  @Test
  void viewShifts() throws ParseException {
    // given
    long userId = 123L;
    List<ShiftEntity> shiftsMock = new ArrayList<>() {{
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("15-08-2022")));
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("16-08-2022")));
      add(new ShiftEntity(userId, 8, DATE_FORMAT.parse("17-08-2022")));
    }};

    when(shiftRepository.findByUserId(userId)).thenReturn(shiftsMock);

    // when
    List<ShiftEntity> actualShifts = shiftService.findAllByUser(userId);

    // then
    verify(shiftRepository, times(1)).findByUserId(123L);
    assertEquals(shiftsMock, actualShifts);
  }

  @Test
  void createShiftTest() throws ParseException {
    // given
    ShiftEntity shift = new ShiftEntity(123L, 8, DATE_FORMAT.parse("15-08-2022"));
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
    ShiftEntity shift = new ShiftEntity(123L, 8, DATE_FORMAT.parse("15-08-2022"));
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