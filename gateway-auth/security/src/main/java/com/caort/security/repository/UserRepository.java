package com.caort.security.repository;

import com.caort.security.pojo.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Caort
 * @date 2022/2/20 10:19
 */
public interface UserRepository extends JpaRepository<SystemUser, Integer> {
    Optional<SystemUser> findByUsername(String username);
}
