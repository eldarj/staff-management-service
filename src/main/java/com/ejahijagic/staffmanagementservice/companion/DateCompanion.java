package com.ejahijagic.staffmanagementservice.companion;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.validator.GenericValidator.isDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class DateCompanion {

  public static final String DATE_STRING_FORMAT = "dd-MM-yyyy";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_STRING_FORMAT);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public static final class InvalidDateException extends RuntimeException {

    public InvalidDateException(String date) {
      super(String.format("%s is in invalid, please supply " + DATE_FORMAT.toPattern(), date));
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public static final class InvalidDateFilterLengthException extends RuntimeException {

    public InvalidDateFilterLengthException() {
      super("Supplied filter date span cannot be larger than a year");
    }
  }

  public Date parse(String from) {
    if (Strings.isEmpty(from)) {
      return null;
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

  public boolean isDateFilterValidLength(Date dateFrom, Date dateTo) {
    if (Objects.nonNull(dateTo) && Objects.nonNull(dateFrom)) {
      long dateSpanMillis = dateTo.getTime() - dateFrom.getTime();

      if (MILLISECONDS.toDays(dateSpanMillis) > 365) {
        throw new InvalidDateFilterLengthException();
      }

      return true;
    }

    return false;
  }
}
