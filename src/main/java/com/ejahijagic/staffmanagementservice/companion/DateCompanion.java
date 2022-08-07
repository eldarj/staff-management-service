package com.ejahijagic.staffmanagementservice.companion;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.apache.commons.validator.GenericValidator.isDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class DateCompanion {

  public static final String DATE_STRING_FORMAT = "dd-MM-yyyy";

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_STRING_FORMAT);

  private static final String MESSAGE =
      "%s is in invalid format, please supply " + DATE_FORMAT.toPattern();

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public static final class InvalidDateException extends RuntimeException {

    public InvalidDateException(String date) {
      super(String.format(MESSAGE, date));
    }
  }

  public Date parse(String from) {
    if (Strings.isEmpty(from)) {
      return getDefaultFromDateFilter();
    }

    if (!isDate(from, DATE_STRING_FORMAT, true)) {
      throw new InvalidDateException(from);
    }

    try {
      return DATE_FORMAT.parse(from);
    } catch (ParseException e) {
      throw new InvalidDateException(from);
    }
  }

  private Date getDefaultFromDateFilter() {
    return new Date(Instant.now().minus(365, DAYS).toEpochMilli());
  }
}
