package com.ejahijagic.staffmanagementservice.companion;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ejahijagic.staffmanagementservice.exception.InvalidDateFilterLengthException;
import java.text.ParseException;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateCompanionTest {

  DateCompanion dateCompanion = new DateCompanion();

  @Test
  void parseTest() {
    // given
    var date = "25-10-2022";

    // when
    Date parsedDate = dateCompanion.parse(date);

    // then
    assertEquals(date, DATE_FORMAT.format(parsedDate));
  }

  @Test
  void parseEmptyDateReturnsNullTest() {
    // given
    var date = "";

    // when
    Date parsedDate = dateCompanion.parse(date);

    // then
    assertNull(parsedDate);
  }

  @Test
  void parseInvalidFormatThrowsTest() {
    // given
    var invalidDate = "10.2022";

    // when
    Exception exception = assertThrows(Exception.class, () -> {
      dateCompanion.parse(invalidDate);
    });

    // then
    String expectedMessage = "10.2022 is in invalid, please supply yyyy-MM-dd";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void throwsResponseExceptionWhenDateFilterSuppliedIsLargerThan1YearTest() throws ParseException {
    // given
    var from = DATE_FORMAT.parse("2021-1-1");
    var to = DATE_FORMAT.parse("2022-9-10");

    // when
    Exception exception = assertThrows(InvalidDateFilterLengthException.class, () -> {
      dateCompanion.isDateFilterValidLength(from, to);
    });

    // then
    var expectedMessage = "Supplied filter date span cannot be larger than a year";
    var actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void returnsFalseIfSuppliedFilterIsNotValidTest() throws ParseException {
    // given
    var from = DATE_FORMAT.parse("2022-5-10");

    // when
    boolean isValid = dateCompanion.isDateFilterValidLength(from, null);
    boolean isValid2 = dateCompanion.isDateFilterValidLength(null, from);
    boolean isValid3 = dateCompanion.isDateFilterValidLength(null, null);

    // then
    assertFalse(isValid);
    assertFalse(isValid2);
    assertFalse(isValid3);
  }

  @Test
  void returnsTrueIfSuppliedFilterIsntValidTest() throws ParseException {
    // given
    var from = DATE_FORMAT.parse("2022-5-10");
    var to = DATE_FORMAT.parse("2022-9-10");

    // when
    boolean isValid = dateCompanion.isDateFilterValidLength(from, to);

    // then
    assertTrue(isValid);
  }
}