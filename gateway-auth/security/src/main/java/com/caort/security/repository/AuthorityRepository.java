package com.caort.security.repository;

import com.caort.security.pojo.SystemAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Caort
 * @date 2022/2/20 10:20
 */
public interface AuthorityRepository extends JpaRepository<SystemAuthority, Integer> {
}
