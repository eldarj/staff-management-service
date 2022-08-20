package com.ejahijagic.staffmanagementservice.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  List<UserEntity> findAll();

  UserEntity findByUsername(String username);
}
