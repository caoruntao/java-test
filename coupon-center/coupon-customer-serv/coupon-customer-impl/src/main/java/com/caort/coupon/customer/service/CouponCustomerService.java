package com.caort.coupon.customer.service;

import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;
import com.caort.coupon.customer.api.beans.RequestCoupon;
import com.caort.coupon.customer.api.beans.SearchCoupon;
import com.caort.coupon.customer.dao.entity.Coupon;
import com.caort.coupon.template.api.beans.CouponInfo;

import java.util.List;

/**
 * @author caort
 * @date 2022/8/25 08:59
 */
public interface CouponCustomerService {
    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    void deleteCoupon(Long userId, Long couponId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);
}
