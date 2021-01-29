package com.cirko.xdaytrade.dao.user;

import com.cirko.xdaytrade.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.security.Permission;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    User findByPhone(String phone);

    User findByUsername(String userName);

}
