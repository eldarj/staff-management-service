package com.ejahijagic.staffmanagementservice.shifts.data;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends CrudRepository<ShiftEntity, Long> {

  List<ShiftEntity> findByDateGreaterThanEqualAndDateLessThanEqual(Date from, Date to);

  List<ShiftEntity> findByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
      long userId, Date from, Date to);
}
