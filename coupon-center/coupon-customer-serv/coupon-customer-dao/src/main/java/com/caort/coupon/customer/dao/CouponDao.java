package com.caort.coupon.customer.dao;

import com.caort.coupon.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author caort
 * @date 2022/8/25 08:50
 */
public interface CouponDao extends JpaRepository<Coupon, Long> {
    Long countByUserIdAndTemplateId(Long userId, Long couponTemplateId);
}
