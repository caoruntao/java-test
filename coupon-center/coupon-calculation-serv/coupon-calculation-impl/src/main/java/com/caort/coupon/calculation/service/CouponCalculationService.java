package com.caort.coupon.calculation.service;

import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;

/**
 * @author Caort
 * @date 2022/8/22 20:33
 */
public interface CouponCalculationService {
    ShoppingCart calculateOrderPrice(ShoppingCart order);

    SimulationResponse simulateOrder(SimulationOrder order);
}
