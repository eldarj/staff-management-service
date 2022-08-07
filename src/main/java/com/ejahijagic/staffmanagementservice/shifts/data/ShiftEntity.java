package com.ejahijagic.staffmanagementservice.shifts.data;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long userId;

  private Date date;

  private int lengthHours;

  public ShiftEntity(long userId, int lengthHours, Date date) {
    this.userId = userId;
    this.lengthHours = lengthHours;
    this.date = date;
  }
}
