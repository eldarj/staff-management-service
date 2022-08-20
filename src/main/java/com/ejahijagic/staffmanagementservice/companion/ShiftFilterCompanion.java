package com.ejahijagic.staffmanagementservice.companion;

import static java.time.temporal.ChronoUnit.DAYS;

import com.ejahijagic.staffmanagementservice.companion.DateCompanion;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public record ShiftFilterCompanion(DateCompanion dateCompanion) {

  public record ShiftFilter(Date dateFrom, Date dateTo) { }

  public ShiftFilter of(String from, String to) {
    var dateTo = dateCompanion.parse(to);
    var dateFrom = dateCompanion.parse(from);

    if (dateCompanion.isDateFilterValidLength(dateFrom, dateTo)) {
      return new ShiftFilter(dateFrom, dateTo);
    }

    if (Objects.isNull(dateTo) && Objects.isNull(dateFrom)) {
      return createDefaultFilter();
    } else if (Objects.isNull(dateTo)) {
      return new ShiftFilter(dateFrom, createToDate(dateFrom));
    } else {
      return new ShiftFilter(createFromDate(dateTo), dateTo);
    }
  }

  private ShiftFilter createDefaultFilter() {
    Date to = new Date(Instant.now().plus(182, DAYS).toEpochMilli());
    Date from = new Date(Instant.now().minus(182, DAYS).toEpochMilli());
    return new ShiftFilter(from, to);
  }

  private Date createToDate(Date from) {
    return new Date(from.toInstant().plus(365, DAYS).toEpochMilli());
  }

  private Date createFromDate(Date to) {
    return new Date(to.toInstant().minus(365, DAYS).toEpochMilli());
  }
}
