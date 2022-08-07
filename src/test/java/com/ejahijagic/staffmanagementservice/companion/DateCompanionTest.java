package com.ejahijagic.staffmanagementservice.companion;

import static com.ejahijagic.staffmanagementservice.companion.DateCompanion.DATE_FORMAT;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
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
  void parseNullDateReturnsDefaultTest() {
    // given
    var date = "";

    // when
    Date parsedDate = dateCompanion.parse(date);

    // then
    Date expectedDate = new Date(Instant.now().minus(365, DAYS).toEpochMilli());
    assertEquals(expectedDate.toString(), parsedDate.toString());
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
    String expectedMessage = "10.2022 is in invalid format, please supply dd-MM-yyyy";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}