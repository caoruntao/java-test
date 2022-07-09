package com.caort.spring.batch.repository;

import com.caort.spring.batch.pojo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Caort
 * @date 2022/7/9 10:26
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.status = 'completed'")
    List<Order> findByStatus();
}
