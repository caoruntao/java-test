package com.caort.security.repository;

import com.caort.security.pojo.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Caort
 * @date 2022/2/20 10:19
 */
public interface RoleRepository extends JpaRepository<SystemRole, Integer> {
}
