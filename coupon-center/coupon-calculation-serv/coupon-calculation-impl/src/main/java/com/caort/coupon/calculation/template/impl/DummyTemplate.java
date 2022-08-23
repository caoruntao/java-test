package com.caort.coupon.calculation.template.impl;

import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.template.AbstractRuleTemplate;
import com.caort.coupon.template.api.beans.rules.Discount;
import org.springframework.stereotype.Component;

/**
 * 空实现
 *
 * @author caort
 * @date 2022/8/23 14:27
 */
@Component
public class DummyTemplate extends AbstractRuleTemplate {
    @Override
    public ShoppingCart calculate(ShoppingCart settlement) {
        long totalCost = getTotalCost(settlement.getProductList());
        settlement.setCost(totalCost);
        return settlement;
    }

    @Override
    protected long calculateDiscountCost(long cost, Discount discount) {
        return cost;
    }
}
