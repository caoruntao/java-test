package com.caort.coupon.calculation.service.impl;

import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;
import com.caort.coupon.calculation.service.CouponCalculationService;
import com.caort.coupon.calculation.template.CouponTemplateFactory;
import com.caort.coupon.calculation.template.RuleTemplate;
import com.caort.coupon.template.api.beans.CouponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Caort
 * @date 2022/8/22 20:34
 */
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {

    @Autowired
    private CouponTemplateFactory couponTemplateFactory;

    @Override
    public ShoppingCart calculateOrderPrice(ShoppingCart order) {
        RuleTemplate template = couponTemplateFactory.getTemplate(order);
        return template.calculate(order);
    }

    @Override
    public SimulationResponse simulateOrder(SimulationOrder order) {
        SimulationResponse response = new SimulationResponse();
        long minOrderPrice = Long.MAX_VALUE;

        // 计算每一个优惠券的订单价格
        for (CouponInfo coupon : order.getCouponInfoList()) {
            ShoppingCart cart = new ShoppingCart();
            cart.setProductList(order.getProductList());
            cart.setCouponInfoList(List.of(coupon));
            cart = couponTemplateFactory.getTemplate(cart).calculate(cart);

            Long couponId = coupon.getId();
            long orderPrice = cart.getCost();

            // 设置当前优惠券对应的订单价格
            response.getCouponToOrderPrice().put(couponId, orderPrice);

            // 比较订单价格，设置当前最优优惠券的ID
            if (minOrderPrice > orderPrice) {
                response.setBestCouponId(coupon.getId());
                minOrderPrice = orderPrice;
            }
        }
        return response;
    }
}
